package com.travel.web_oasis.config;

import com.travel.web_oasis.config.oauth.service.CustomOAuth2Service;
import com.travel.web_oasis.config.oauth.service.CustomUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOAuth2Service customOAuth2UserService;
    private final CustomUserDetailService customUserDetailService;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable);

        http.userDetailsService(customUserDetailService);

        http.authorizeHttpRequests(request -> {
            request.requestMatchers("/member/**","/member/profile").permitAll()
                    .requestMatchers("/","/post/**").hasRole("USER")
                    .requestMatchers("/member/login").anonymous()
                    .requestMatchers("/member/logout").authenticated()
                    .requestMatchers("/img/**", "/css/**", "/webjars/**","/js/**").permitAll();
        });


        http.formLogin(formLogin -> {
            formLogin.loginPage("/member/login")
                    .usernameParameter("email")
                    .defaultSuccessUrl("/")
                    .failureUrl("/member/login/error")
                    .permitAll();
        });
        http.logout(logout ->{
           logout.logoutSuccessUrl("/")
                   .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                   .logoutSuccessUrl("/member/login") // 로그아웃 후 이동할 페이지의 경로
                   .invalidateHttpSession(true) // 세션 무효화
                   .deleteCookies("JSESSIONID"); // 쿠키 삭제

        });
        http.oauth2Login(oauth ->{
            oauth.userInfoEndpoint(userInfoEndpointConfig -> {
                userInfoEndpointConfig.userService(customOAuth2UserService);
            });
            oauth.defaultSuccessUrl("/");
            oauth.failureUrl("/member/login/error");

        });

        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
