package com.travel.web_oasis.controller;

import com.travel.web_oasis.domain.member.Member;
import com.travel.web_oasis.domain.service.MemberService;
import com.travel.web_oasis.domain.service.MemberServiceImpl;
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

    private final MemberServiceImpl memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("memberDTO",new MemberDTO());
        return "/member/loginForm";
    }

    @GetMapping("/login/error")
    public String loginError(Model model) {
        model.addAttribute("message","이메일 또는 비밀번호를 확인해주세요");
        return "/member/loginForm";
    }
    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("memberDTO", new MemberDTO());
        return "/member/registerForm";
    }


    @PostMapping("/register")
    public String registerForm(@Valid MemberDTO memberDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "member/registerForm";
        }
        try {
            Member member = Member.register(memberDTO, passwordEncoder);
            Long id = memberService.saveMember(member);
            model.addAttribute("message","회원가입에 성공하셨습니다.");
        } catch (IllegalStateException e) {
            model.addAttribute("message", "회원가입에 실패했습니다. 관리자에게 문의하세요");
            return "member/registerForm";
        }

        return "redirect:/";
    }
}
