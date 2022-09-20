package me.blog.haema.global.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import me.blog.haema.domain.member.error.MemberNotFoundException;
import me.blog.haema.domain.member.repository.MemberRepository;
import me.blog.haema.global.error.exception.ErrorCode;
import me.blog.haema.global.security.CustomUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TokenProvider implements InitializingBean {

    private final Logger logger = LoggerFactory.getLogger(TokenProvider.class);

    private static final String AUTHORITIES_KEY = "role";

    // InitializingBean 를 상속받아 afterPropertiesSet() 를 구현
    // Bean 생성 후, 주입 받아 secret 값을 Base64 Decode 해서 key 변수에 할당

    private final String secret;
    private final long accessTokenValidityInMilliseconds;
    private final long refreshTokenValidityInMilliseconds;
    private final MemberRepository memberRepository;

    private Key key;

    public TokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.accessToken-validity-in-seconds}") long accessTokenValidityInMilliseconds,
            @Value("${jwt.refreshToken-validity-in-seconds}") long refreshTokenValidityInMilliseconds,
            MemberRepository memberRepository) {
        this.secret = secret;
        this.accessTokenValidityInMilliseconds = accessTokenValidityInMilliseconds * 1000;
        this.refreshTokenValidityInMilliseconds = refreshTokenValidityInMilliseconds * 1000;
        this.memberRepository = memberRepository;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    /** Authentication 객체의 권한 정보를 이용해서 토큰을 생성하기 위한 createToken 메소드 */
    public TokenDto createToken(Authentication authentication){

        String authorities = authentication.getAuthorities().stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.joining(","));

        log.debug("authorities : {}", authorities);

        long now = (new Date()).getTime();
        Date validity = new Date(now + this.accessTokenValidityInMilliseconds);

        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();

        String accessToken = Jwts.builder()
                .claim("id", principal.getId())
                .claim(AUTHORITIES_KEY, authorities)
                .setExpiration(new Date(now + accessTokenValidityInMilliseconds))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        String refreshToken = Jwts.builder()
                .claim(AUTHORITIES_KEY, authorities)
                .claim("id", principal.getId())
                .setExpiration(new Date(now + refreshTokenValidityInMilliseconds))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        return TokenDto.of(accessToken, refreshToken);
    }

    /** Token 에 담겨있는 정보를 Authentication 객체로 return 하는 메소드 */
    public Authentication getAuthentication(String token) {

        // Token 을 Parameter 로 받아서 Claims 를 생성
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        String id = String.valueOf(claims.get("id"));

        log.info("claims.getId()");
        log.info(claims.getId());

        // Claims 에서 권한 정보를 빼내어, CustomUserDetails 라는 유저 객체를 생성
        CustomUserDetails principal = CustomUserDetails.of(memberRepository.findById(Long.valueOf(id)).orElseThrow(
                () -> new MemberNotFoundException(ErrorCode.USER_NOT_FOUND)));

        // CustomUserDetails 유저 객체, token, 권한 정보를 가지고 최종적으로 Authentication 객체를 return
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    /** 토큰의 유효성을 검증 하기 위한 validateToken 메소드 */
    public boolean validateToken(String token) {

        // Token 을 Parameter 로 받아서 parsing 을 했을 때 문제가 없으면 true, 있으면 false
        // 이 때, 익셉션들을 catch 해주는 로직을 붙인다.
       try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;

        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            logger.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            logger.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            logger.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            logger.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }
}