package com.travel.web_oasis.config.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //헤더에서 JWT를 받아옵니다.
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);

        //유효한 토근인지 확인
        if (token != null && jwtTokenProvider.validateToken(token)) {

            //토큰 인증 과정을 거친 결과를 authentication이라는 이름으로 저장
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            // SecurityContext 에 Authentication 객체를 저장합니다.
            // token이 인증된 상태를 유지하도록 context(맥락)을 유지해줌
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        //UsernamePasswordAuthenticationFilter로 이동
        chain.doFilter(request, response);
    }
}
