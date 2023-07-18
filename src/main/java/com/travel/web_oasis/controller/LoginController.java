package com.travel.web_oasis.controller;

import com.travel.web_oasis.config.jwt.JwtTokenProvider;
import com.travel.web_oasis.domain.service.MemberService;
import com.travel.web_oasis.web.dto.MemberDTO;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/auth")
@RestController
public class LoginController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public String authenticateUser(@RequestBody MemberDTO memberDTO, HttpServletResponse response) {

        System.out.println("memberDTO = " + memberDTO);
        MemberDTO member = memberService.login(memberDTO);
        String email = member.getEmail();
        String provider = member.getProvider();

        String token = jwtTokenProvider.createToken(email,provider);
        response.setHeader("JWT", token);
        return token;
    }
}
