package com.travel.web_oasis.controller;

import com.travel.web_oasis.config.oauth.dto.PrincipalDetail;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final HttpSession httpSession;
    @GetMapping("/")
    public String index(@AuthenticationPrincipal PrincipalDetail principal, Model model) {

        if (principal != null) {
            model.addAttribute("member", principal.getMember());
            httpSession.setAttribute("member",principal.getMember());
        }
        return "/main";
    }
}