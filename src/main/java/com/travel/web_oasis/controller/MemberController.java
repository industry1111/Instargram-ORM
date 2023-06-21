package com.travel.web_oasis.controller;

import com.travel.web_oasis.domain.member.Member;
import com.travel.web_oasis.domain.service.MemberService;
import com.travel.web_oasis.web.dto.MemberDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("memberDTO",new MemberDTO());
        return "/member/loginForm";
    }

    @PostMapping("/login")
    public String loginForm(MemberDTO memberDTO) {
        return "redirect:/";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("memberDTO", new MemberDTO());
        return "/member/registerForm";
    }

    @PostMapping("/register")
    public String registerForm(@Valid MemberDTO memberDTO, BindingResult bindingResult, Model model) {
        System.out.println("memberDTO.toString() = " + memberDTO.toString());
        if (bindingResult.hasErrors()) {
            return "member/registerForm";
        }
        try {
            Member member = Member.register(memberDTO, passwordEncoder);
            memberService.saveMember(member);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "member/registerForm";
        }

        return "redirect:/";
    }
}
