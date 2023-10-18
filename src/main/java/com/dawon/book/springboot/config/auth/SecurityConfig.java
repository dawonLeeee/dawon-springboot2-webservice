package com.dawon.book.springboot.config.auth;

import com.dawon.book.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity // Spring Security 설정을 활성ㅇ화시켜줌
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().headers().frameOptions().disable() // h2-console화면 사용을 위해 해당 옵션들 disable
                .and()
                .authorizeRequests() // url별 권한관리를 설정하는 옵션의 시작점. authorizeRequests가 선언되어야만 antMatchers옵션 사용가능
                .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll()
                // antMatchers : 권한 관리대상을 지정하는 옵션. url, http메서드별로 관리가능
                .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                .anyRequest().authenticated() // 설정된 값들 이외 나머지 url. anyRequest().authenticated() : 나머지 url은 인증된 사용자에게만 허용
                .and()
                .logout().logoutSuccessUrl("/") // 로그아웃에 대한 여러 설정의 진입점
                .and()
                .oauth2Login().userInfoEndpoint().userService(customOAuth2UserService);
        // userInfoEndpoint : OAuth2 로그인 성공 후 사용자 정보를 가져올때의 설정 담당
        // userService : 소셜로그인 성공시 후속조치를 진행할 UserService인터페이스의 구현체를 등록함.
    }
}
