package me.blog.haema.global.jwt;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@AllArgsConstructor
public class JwtFilter extends GenericFilterBean {

    public final Logger logger = LoggerFactory.getLogger(TokenProvider.class);

    public static final String AUTHORIZATION_HEADER = "Authorization";

    // TokenProvider 를 주입 받는다
    private TokenProvider tokenProvider;

    /** 필터링 로직 doFilter 내부에 작성 */
    // Token 인증 정보를 SecurityContext 에 저장하는 역할
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // request 를 받아서 resolveToken 메소드를 통해 Token 정보를 꺼낸다.
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        String jwt = resolveToken(servletRequest);
        String requestURI = servletRequest.getRequestURI();

        // jwt 변수에 담은 Token 정보를 TokenProvider 에 만든 validateToken 메소드를 통해 유효성 검사를 한다.
        if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
            // 유효성 검사를 통과하면, Token 에 담겨있는 정보를 이용해 getAuthentication 메소드를 통해 Authentication 객체로 return.
            Authentication authentication = tokenProvider.getAuthentication(jwt);
            // SecurityContext 에 저장해준다.
            SecurityContextHolder.getContext().setAuthentication(authentication);
            logger.debug("Security Context 에 '{}' 인증 정보를 저장했습니다, uri: {}", authentication.getName(), requestURI);
        } else {
            logger.debug("유효한 JWT 토큰이 없습니다, uri: {}", requestURI);
        }

        chain.doFilter(request, response);
    }

    /** Request Header 에서 Token 정보를 꺼내오기 위한 resolveToken 메소드 */
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}