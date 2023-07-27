package com.travel.web_oasis.controller;

import com.travel.web_oasis.config.jwt.JwtTokenProvider;
import com.travel.web_oasis.config.oauth.dto.PrincipalDetail;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
public class IndexController {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @GetMapping("/main")
    public String index(HttpServletRequest request, Model model) {
        System.out.println("indexStart");
        Cookie[] cookies = request.getCookies();

        String jwtToken = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("JWT")) {
                    jwtToken = cookie.getValue();
                    break;
                }
            }
        }
        // 토큰을 이용하여 인증 정보를 추출하거나 다른 작업을 수행
        Authentication authentication = jwtTokenProvider.getAuthentication(jwtToken);
        if (authentication != null) {
            // 인증된 사용자 정보를 활용하여 원하는 작업 수행
            PrincipalDetail principalDetail = (PrincipalDetail) authentication.getPrincipal();

            model.addAttribute("member", principalDetail.getMember());
        }

        return "main";
    }
}