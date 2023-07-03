package com.travel.web_oasis.domain.service;

import com.travel.web_oasis.domain.member.Member;
import com.travel.web_oasis.domain.member.Role;
import com.travel.web_oasis.domain.member.Status;
import com.travel.web_oasis.domain.repository.MemberRepository;
import com.travel.web_oasis.web.dto.MemberDTO;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberServiceImpl memberService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    MemberDTO newMember() {
        return MemberDTO.builder()
                .name("테스트")
                .email("test@mail.com")
                .password("1234")
                .role(Role.USER)
                .status(Status.PUBLIC)
                .build();
    }

    MemberDTO update() {
        return MemberDTO.builder()
                .name("테스트2")
                .password("123")
                .status(Status.PRIVATE)
                .build();
    }

    @Test
    void saveMember() {
        memberService.saveMember(newMember());
    }

    @Test
    void deleteMember() {

        Long id = memberService.saveMember(newMember());

        Member member = memberRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        memberRepository.deleteById(member.getId());

    }

    @Test
    void updateMember() {
        Long id = memberService.saveMember(newMember());
        MemberDTO memberDTO = update();
        memberDTO.setId(id);
        memberService.updateMember(memberDTO);

        Member member = memberRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        System.out.println(member.getId());

        Assertions.assertEquals(member.getName(), "테스트2");

    }
}