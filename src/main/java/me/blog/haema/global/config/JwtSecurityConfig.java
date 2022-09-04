package me.blog.haema.global.config;

import lombok.RequiredArgsConstructor;
import me.blog.haema.global.jwt.JwtFilter;
import me.blog.haema.global.jwt.TokenProvider;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    /**
     * Filter : doFilter 메소드를 통해 각각 request(요청)를 resource 로, resource 로부터 response(응답)를 수행
     * SecurityFilterChain : HttpServletRequest match 여부, filter 를 정의하기 위한 interface
     *
     * JWT (JSON Web Token) : 안전하게 전송하기 위해 고안된 JSON 개체의 데이터 (key-value)
     * */

    private final TokenProvider tokenProvider;

    @Override
    public void configure(HttpSecurity builder) throws Exception {
        JwtFilter customFilter = new JwtFilter(tokenProvider);
        builder.addFilterBefore(customFilter, // addFilterBefore : 앞에 필터를 추가
                UsernamePasswordAuthenticationFilter.class); // 유저 name, password 를 인증 처리하는 Filter
    }
}