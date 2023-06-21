package com.travel.web_oasis.controller;

import com.travel.web_oasis.domain.member.Member;
import com.travel.web_oasis.domain.member.Role;
import com.travel.web_oasis.domain.member.Status;
import com.travel.web_oasis.domain.service.MemberService;
import com.travel.web_oasis.web.dto.MemberDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Member testMember(String email, String password) {
        MemberDTO memberDTO = MemberDTO.builder()
                .email(email)
                .name("테스터")
                .password(password)
                .phone("01000000000")
                .role(Role.USER)
                .status(Status.PUBLIC)
                .build();

        return Member.register(memberDTO, passwordEncoder);
    }

    @Test
    public void loginForm() throws Exception {
        String email = "test@mail.com";
        String password = "12345678";

        this.testMember(email, password);

        mockMvc.perform(formLogin().userParameter("email")
                        .loginProcessingUrl("/member/login")
                        .user(email).password(password))
                .andExpect(SecurityMockMvcResultMatchers.unauthenticated());
    }

    @Test
    void testLoginForm() {
    }

    @Test
    void registerForm() {
    }

    @Test
    void testRegisterForm() {
    }
}