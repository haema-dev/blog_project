package me.blog.haema.global.config;

import lombok.RequiredArgsConstructor;
import me.blog.haema.global.jwt.JwtAccessDeniedHandler;
import me.blog.haema.global.jwt.JwtAuthenticationEntryPoint;
import me.blog.haema.global.jwt.TokenProvider;
import me.blog.haema.global.security.CustomAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration // config 임을 알려주는 어노테이션
@EnableWebSecurity // 웹 보안 설정을 위해 필요한 어노테이션 (ex: SecurityFilterChain) // https://spring.io/guides/gs/securing-web/
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 인증 (Authentication) : 유저 확인
     * 인가 (Authorization) : 권한 허락
     * */

    private static final String PUBLIC = "/api/v1/public/**"; // public url 정책

    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/h2-console/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                /** 쿠키-세션 기반 disable */
                .csrf()
                    .disable()

                /** Token 인증 시, 세션 강제 종료 */
                // 서버에서 관리되는 세션 없이 클라이언트에서 요청하는 헤더에 token 을 담아보내는 인증방식을 사용
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()

                /** 인증 진입점(authenticationEntryPoint)으로 사용할 것을 설정 */
                // 설정하지 않을 시
                // 401 Unauthorized : 자격 증명 제공 필요
                // 403 Forbidden : 필요한 권한을 찾을 수 없음
                .exceptionHandling()
                    .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                    .accessDeniedHandler(jwtAccessDeniedHandler)
                    .and()

                /**  인가 요청(authorizeRequests): 권한 및 url 관련 설정 */
                // permitAll()은 누구나 허용
                .authenticationProvider(customAuthenticationProvider())
                .authorizeRequests()
                    .antMatchers(HttpMethod.GET, "/").permitAll()
                    .antMatchers(HttpMethod.GET, PUBLIC).permitAll()
                    .antMatchers(HttpMethod.POST, PUBLIC).permitAll()
                    .anyRequest().authenticated()
                    .and()

                /** Token 생성 및 유효성 검증을 위한 TokenProvider 를 생성  */
                // JwtFilter 를 SecurityConfig 에 적용할 JwtSecurityConfig 를 생성
                .apply(new JwtSecurityConfig(tokenProvider))
                    .and()

                .formLogin().disable();
    }

    @Bean
    public CustomAuthenticationProvider customAuthenticationProvider() {
        return new CustomAuthenticationProvider();
    }
}