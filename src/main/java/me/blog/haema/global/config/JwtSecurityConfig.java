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

    private final TokenProvider tokenProvider;

    // TokenProvider 를 주입받아서 상속받은 SecurityConfigurerAdapter 에서 제공하는 configure 메소드에
    // JwtFilter 를 통해서 Security 로직에 필터로 등록 (addFilterBefore : 기존 필터 이전에 필터 하나 추가 등록)
    @Override
    public void configure(HttpSecurity http) throws Exception {

        // JwtFilter 에서 TokenProvider 를 이용하여 Token 을 생성하거나 유효성 검사를 진행
        JwtFilter customFilter = new JwtFilter(tokenProvider);
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);

    }
}