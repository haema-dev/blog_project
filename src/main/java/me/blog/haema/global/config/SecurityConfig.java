package me.blog.haema.global.config;


import lombok.RequiredArgsConstructor;
import me.blog.haema.global.secutiry.CustomAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration // config 임을 알려주는 어노테이션
@EnableWebSecurity // 웹 보안 설정을 위해 필요한 어노테이션 (ex: SecurityFilterChain) // https://spring.io/guides/gs/securing-web/
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 인증 (Authentication) : 유저 확인
     * 인가 (Authorization) : 권한 허락
     * */

    //spring security 에 내장되어있는 암호화 메소드 (BCrypt 해시함수)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //요청 거절 및 권한 거절(403 error)을 해결하기 위해 우선 인증을 받기 위한 provider 생성
    @Bean
    public CustomAuthenticationProvider customAuthenticationProvider() {
        return new CustomAuthenticationProvider();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //configure(WebSecurity web) 는 특정 요청을 무시하도록 재정의하기 위해 사용하는 메소드
        //spring security 를 무시
        //만약 취약점으로부터 이를 보호하고 싶을 경우, configure(HttpSecurity) 에 재정의 필요

        //***현재 무시해야할 경로 존재하지 않아서 설정하지 않음
        super.configure(web);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // CSRF attack : 클라이언트가 보낸 데이터를 바꿔치기하여 보내는 케이스
        // ex) https://portswigger.net/web-security/csrf
        //super.configure(http); //super 호출 안하면 login 창이 뜨도록 탈취 x

        http.csrf().disable() // Disable CSRF protection
                .exceptionHandling() // Allows configuring exception handling
                .and()
                .authorizeRequests() // 인가 (권한 허가)
                .anyRequest().authenticated(); // 인증 (유저 확인)

        http.formLogin().disable(); // formLogin disable
                                    // form 없애면 confirm 창으로 대체되어 뜬다
    }
}
