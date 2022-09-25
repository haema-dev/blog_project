package me.blog.haema.domain.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.blog.haema.domain.auth.error.TokenNotFoundException;
import me.blog.haema.domain.member.repository.MemberRepository;
import me.blog.haema.global.error.exception.ErrorCode;
import me.blog.haema.global.jwt.TokenDto;
import me.blog.haema.global.jwt.TokenProvider;
import me.blog.haema.global.security.CustomUserDetails;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder managerBuilder;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public TokenDto login(final String email, final String password) {

        CustomUserDetails userDetails = CustomUserDetails.of(memberRepository.findByEmail(email));

        UsernamePasswordAuthenticationToken token
                = new UsernamePasswordAuthenticationToken(userDetails, password);

        Authentication authenticate = managerBuilder.getObject().authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        return tokenProvider.createToken(authenticate);
    }

    public String reissue(final String refreshToken) {

        log.debug("auth service reissue : {}", refreshToken);

        if (!tokenProvider.validateToken(refreshToken)) {
            throw new TokenNotFoundException(ErrorCode.TOKEN_NOT_FOUND);
        }

        Authentication authentication = tokenProvider.getAuthentication(refreshToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return tokenProvider.createToken(authentication).getAccessToken();
    }

}
