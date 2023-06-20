package com.travel.web_oasis.domain.service;

import com.travel.web_oasis.domain.member.Member;
import com.travel.web_oasis.domain.member.Role;
import com.travel.web_oasis.domain.member.Status;
import com.travel.web_oasis.domain.repository.MemberRepository;
import com.travel.web_oasis.web.dto.MemberDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    MemberDTO newMember() {
        return MemberDTO.builder()
                .name("테스트")
                .email("test@mail.com")
                .password("1234")
                .phone("01012345678")
                .role(Role.USER)
                .status(Status.PUBLIC)
                .build();
    }

    @Test
    void saveMember() {
        memberService.saveMember(newMember());
    }

    @Test
    void findById() {
        Long id  = memberService.saveMember(newMember());

        Member result = memberService.findById(id);
        assertEquals(result.getName(),"테스트");

        System.out.println("result = " + result.toString());

    }

    @Test
    void deleteMember() {
        Long id = memberService.saveMember(newMember());

        Member member = memberService.findById(id);

        memberService.deleteMember(member.getId());

        Member result = memberService.findById(id);

        assertThat(result).isNull();

    }

    @Test
    void updateMember() {
        Long id = memberService.saveMember(newMember());

        Member member = memberService.findById(id);

        MemberDTO update = MemberDTO.builder()
                        .name("테스트2")
                        .password("123")
                        .status(Status.PRIVATE)
                        .build();

        memberService.updateMember(member.getId(),update);

        Member member1 = memberService.findById(id);

        assertEquals(member1.getStatus(), update.getStatus());


    }
}