package com.travel.web_oasis.config;

import com.travel.web_oasis.config.jwt.JwtTokenProvider;
import com.travel.web_oasis.config.oauth.dto.PrincipalDetail;
import com.travel.web_oasis.domain.member.Member;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OauthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        PrincipalDetail principalDetail = (PrincipalDetail) authentication.getPrincipal();

        Member member = principalDetail.getMember();

        String email = member.getEmail();
        String provider = member.getProvider();

        String jwtToken = jwtTokenProvider.createToken(email,provider);

        // JWT 토큰을 쿠키에 설정
        Cookie cookie = new Cookie("JWT", jwtToken);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        getRedirectStrategy().sendRedirect(request,response,"/main");
    }
}
