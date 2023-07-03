package com.travel.web_oasis.controller;

import com.travel.web_oasis.config.oauth.dto.PrincipalDetail;
import com.travel.web_oasis.domain.service.MemberServiceImpl;
import com.travel.web_oasis.web.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    @Autowired
    private MemberServiceImpl memberService;

    /*
     * @Param
     *
     * @Description : 로그인 페이지로 이동 / 로그인 실패시 error 파라미터를 받아서 메시지를 보내줌.
     *
     * @Return : url > /member/loginForm
     * */
    @GetMapping("/login")
    public String loginForm(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("message", "로그인 정보를 확인해주세요.");
            System.out.println("error = " + error);
        }
        model.addAttribute("memberDTO", new MemberDTO());
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
    public Long registerForm(@ModelAttribute MemberDTO memberDTO) {
        System.out.println("memberDTO = " + memberDTO);
        Long result = memberService.saveMember(memberDTO);
        return result;
    }

    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal PrincipalDetail principalDetail, Model model) {
        System.out.println("principalDetail.getMember() = " + principalDetail.getMember());
        model.addAttribute("member", principalDetail.getMember());
        return "member/profile";
    }

    @GetMapping("/profile/edit")
    public String editProfile(@AuthenticationPrincipal PrincipalDetail principalDetail, Model model) {
        System.out.println("principalDetail.getMember() = " + principalDetail.getMember());
        model.addAttribute("member", principalDetail.getMember());
        return "member/editProfile";
    }

    @ResponseBody
    @PostMapping("/profile/edit/{id}")
    public String editProfile(@PathVariable Long id, @ModelAttribute MemberDTO memberDTO) {
        System.out.println("memberDTO = " + memberDTO);
        memberService.updateMember(memberDTO);
        return "redirect:/member/profile";
    }
}
