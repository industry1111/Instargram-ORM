package com.travel.web_oasis.controller;

import com.travel.web_oasis.config.oauth.dto.SessionSnsMember;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model) {
        SessionSnsMember snsMember = (SessionSnsMember) httpSession.getAttribute("snsMember");
        if (snsMember != null) {
            model.addAttribute("snsMember", snsMember);
        }
        return "/post/postList";
    }
}