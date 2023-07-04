package com.travel.web_oasis.controller;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {
//    @Autowired
//    private MemberServiceImpl memberService;
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    PasswordEncoder passwordEncoder;
//
//    public Member testMember(String email, String password) {
//        MemberDTO memberDTO = MemberDTO.builder()
//                .email(email)
//                .name("테스터")
//                .password(password)
//                .phone("01000000000")
//                .role(Role.USER)
//                .status(Status.PUBLIC)
//                .build();
//
//        return memberService.register(memberDTO,passwordEncoder);
//    }

//    @Test
//    public void loginForm() throws Exception {
//        String email = "test@mail.com";
//        String password = "12345678";
//
//        Long id = memberService.saveMember(testMember(email, password));
//
//        mockMvc.perform(formLogin()
//                .loginProcessingUrl("/member/login")
//                .user("email", email).password(password))
//                .andDo(print())
//                .andExpect(status().is3xxRedirection())
//                .andExpect(SecurityMockMvcResultMatchers.authenticated());
//
//    }
//    @Test
//    public void loginFail() throws Exception {
//        String email = "test@mail.com";
//        String password = "12345678";
//
//        this.testMember(email, "123456");
//
//        mockMvc.perform(formLogin()
//                        .loginProcessingUrl("/member/login")
//                        .user("email", "test1@mail.com").password(password))
//                .andDo(print())
//                .andExpect(SecurityMockMvcResultMatchers.unauthenticated());
//    }
//    @Test
//    void registerForm() {
//    }


}