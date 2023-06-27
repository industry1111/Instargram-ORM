package com.travel.web_oasis.config;

import com.travel.web_oasis.config.oauth.service.CustomOAuth2Service;
import com.travel.web_oasis.domain.service.MemberServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomOAuth2Service customOAuth2UserService;
    @Autowired
    private MemberServiceImpl memberService;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable);

        http.userDetailsService(memberService);

        http.authorizeHttpRequests(request -> {
            request.requestMatchers("/member/**","/post/**").permitAll()
                    .requestMatchers("/js/**","/img/**", "/css/**", "/webjars/**").permitAll()
                    .requestMatchers("/").hasRole("USER");
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

        });

        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
