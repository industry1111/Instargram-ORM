package com.travel.web_oasis.controller;

import com.travel.web_oasis.config.jwt.JwtTokenProvider;
import com.travel.web_oasis.domain.service.member.MemberService;
import com.travel.web_oasis.web.dto.MemberDTO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/auth")
@RestController
public class LoginController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody MemberDTO memberDTO, HttpServletResponse response) {

        MemberDTO member = memberService.login(memberDTO);

        if ( member != null ) {
            String email = member.getEmail();
            String provider = member.getProvider();

            String result = jwtTokenProvider.createToken(email,provider);

            // JWT 토큰을 쿠키에 설정
            Cookie cookie = new Cookie("JWT", result);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            response.addCookie(cookie);

            return ResponseEntity.ok().body(result);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("없는 사용자 입니다.");
        }

    }
}
