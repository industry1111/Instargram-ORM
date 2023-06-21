package com.travel.web_oasis.config;

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
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(request -> request.requestMatchers("/**").hasRole("USER"));

        http.formLogin(formLogin -> {
            formLogin.loginPage("/member/login")
                    .usernameParameter("email")
                    .defaultSuccessUrl("/post/postList")
                    .failureUrl("/member/login?error")
                    .permitAll();
        });
        http.logout(logout ->{
           logout.logoutSuccessUrl("/")
                   .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"));

        });
        http.exceptionHandling(exceptionHandling ->{
            exceptionHandling.authenticationEntryPoint(new CustomAuthenticationEntryPoint());
        });

        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
