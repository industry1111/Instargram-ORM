package com.travel.web_oasis.controller;

import com.travel.web_oasis.domain.service.MemberServiceImpl;
import com.travel.web_oasis.web.dto.MemberDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    @Autowired
    private MemberServiceImpl memberService;

    /*
     * @Param
     *
     * @Description : 로그인 페이지로 이동
     *
     * @Return : url > /member/loginForm
     * */
    @GetMapping("/login")
    public String loginForm(Model model) {

        model.addAttribute("memberDTO",new MemberDTO());
        return "/member/loginForm";
    }
    /*
     * @Param
     *
     * @Description :로그인 error가 있으면 로그인 페이지로 이동
     *
     * @Return : 로그인 페이지로 이동
     * */
    @GetMapping("/login/error")
    public String loginError(Model model) {
        model.addAttribute("message","이메일 또는 비밀번호를 확인해주세요");
        return "/member/loginForm";
    }
    /*
     * @Param
     *
     * @Description : 회원가입 페이지로 이동
     *
     * @Return : 회원가입 페이지
     * */
    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("memberDTO", new MemberDTO());
        return "/member/registerForm";
    }

    /*
     * @Param
     *  MemberDTO : 회원가입 정보를 담은 DTO
     * bindingResult : 유효성 검사 결과를 담은 객체
     *
     * @Description : 회원가입
     *
     * @Return : 로그인 페이지로 이동
     * */
    @ResponseBody
    @PostMapping("/register")
    public  Map<String,String> registerForm(@ModelAttribute MemberDTO memberDTO) {
        System.out.println("memberDTO = " + memberDTO);
        Map<String,String> result = new HashMap<>();
        String message = "";
        try {
            Long id = memberService.saveMember(memberDTO);
            message = "회원가입에 성공하셨습니다.";
        } catch (IllegalStateException e) {
            System.out.println("e.getMessage() = " + e.getMessage());
            message = "회원가입에 실패하셨습니다. 관리자에게 문의해 주세요.";
        }

        result.put("message",message);
        System.out.println("message = " + message);
        return result;
    }

    @GetMapping("/profile")
    public String profile() {
        return "member/profile";
    }
}
