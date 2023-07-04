package com.travel.web_oasis.controller;

import com.travel.web_oasis.config.oauth.dto.PrincipalDetail;
import com.travel.web_oasis.domain.service.MemberService;
import com.travel.web_oasis.domain.service.MemberServiceImpl;
import com.travel.web_oasis.web.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    @Autowired
    private MemberServiceImpl memberService;

    Logger log = org.slf4j.LoggerFactory.getLogger(MemberController.class);
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
        }
        model.addAttribute("memberDTO", new MemberDTO());
        return "/member/loginForm";
    }
    /*
     * @Param : error
     *
     * @Description : 로그인 실패시 로그인 에러 메시지 전송
     *
     * @Return : url > /member/loginForm/error
     * */
    @GetMapping("/login/error")
    public String loginError(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("message", "로그인 정보를 확인해주세요.");
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
     *
     * @Description : 회원가입
     *
     * @Return :성공시 로그인 페이지로 이동
     * */
    @ResponseBody
    @PostMapping("/register")
    public Long registerForm(@RequestBody MemberDTO memberDTO) {
        Long result = memberService.saveMember(memberDTO);
        return result;
    }
    /*
     * @Param :
     *
     * @Description : 프로필 페이지로 이동
     *
     * @Return : url > member/profile
     * */
    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal PrincipalDetail principalDetail, Model model) {
        model.addAttribute("member", principalDetail.getMember());
        return "member/profile";
    }
    /*
     * @Param :
     *
     * @Description : 프로필 수정 페이지로 이동
     *
     * @Return : url > /member/editProfile
     * */
    @GetMapping("/profile/edit")
    public String editProfile(@AuthenticationPrincipal PrincipalDetail principalDetail, Model model) {
        model.addAttribute("member", principalDetail.getMember());
        return "member/editProfile";
    }

    /*
     * @Param : MemberDTO
     *
     * @Description : memberDTO로 받은객체로 멤버 수정 후 수정된 멤버를 반환
     *
     * @Return : url > /member/loginForm
     * */
    @ResponseBody
    @PutMapping("/profile/edit")
    public MemberDTO editProfile(@ModelAttribute MemberDTO memberDTO, @RequestPart(value = "file", required = false)  MultipartFile file, @AuthenticationPrincipal PrincipalDetail principalDetail) throws Exception {
        log.info("editProfile");
        log.info("MemberDTO : " + memberDTO.toString());
        return memberService.updateMember(memberDTO, principalDetail,file);
    }

    @ResponseBody
    @GetMapping("/download/profile/{id}")
    public Resource downloadProfile(@PathVariable Long id) throws MalformedURLException {
        return new UrlResource("file:" + memberService.getFullPath(id));
    }
}
