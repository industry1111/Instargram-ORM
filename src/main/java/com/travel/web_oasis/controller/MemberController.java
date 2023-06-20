package com.travel.web_oasis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
public class MemberController {
    @GetMapping("/login")
    public String loginForm() {
        return "/member/loginForm";
    }

    @GetMapping("/register")
    public String registerForm() {
        return "/member/registerForm";
    }
}
