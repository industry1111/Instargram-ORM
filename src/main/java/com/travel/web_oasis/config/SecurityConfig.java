package com.travel.web_oasis.config;

import com.travel.web_oasis.config.jwt.JwtAuthenticationFilter;
import com.travel.web_oasis.config.jwt.JwtTokenProvider;
import com.travel.web_oasis.config.oauth.service.PrincipalDetailsService;
import com.travel.web_oasis.config.oauth.service.PrincipalOauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final PrincipalDetailsService principalDetailsService;
    private final PrincipalOauth2UserService principalOauth2UserService;
    private final OauthSuccessHandler oauthSuccessHandler;

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable);

        http.userDetailsService(principalDetailsService);


//        URL 인증 여부 설정
        http.authorizeHttpRequests(request -> {
            request.requestMatchers("/member/register" , "/member/login","/error","/auth/login","/main").permitAll()
//                    .requestMatchers("/","/post/**","/comment/**").hasRole("USER")
                    .requestMatchers("/member/login").anonymous()
                    .requestMatchers("/img/**", "/css/**", "/webjars/**","/js/**","/images/**").permitAll()
                    .anyRequest().authenticated();
        });

        //Jwt Filter 추가
        http.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

        http.formLogin(formLogin -> {
            formLogin.loginPage("/member/login");
        });
        http.logout(logout ->{
           logout.logoutSuccessUrl("/member/login")// 로그아웃 후 이동할 페이지의 경로
                   .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                   .invalidateHttpSession(true) // 세션 무효화
                   .deleteCookies("JWT"); // 쿠키 삭제
        });

        http.oauth2Login(oauth ->{
            oauth.userInfoEndpoint(userInfoEndpointConfig -> {
                userInfoEndpointConfig.userService(principalOauth2UserService);
            });
//            oauth.defaultSuccessUrl("/main");
            oauth.successHandler(oauthSuccessHandler);
            oauth.failureUrl("/member/login/error");

        });

        http.sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션 사용 안함
                );

        return http.build();
    }
}
