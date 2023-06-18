package com.travel.web_oasis.domain.service;

import com.travel.web_oasis.domain.member.Member;
import com.travel.web_oasis.domain.member.Role;
import com.travel.web_oasis.domain.member.Status;
import com.travel.web_oasis.domain.repository.MemberRepository;
import com.travel.web_oasis.web.dto.MemberDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    MemberDto newMember() {
        MemberDto memberDto = new MemberDto();
        memberDto.setName("테스트");
        memberDto.setEmail("test@mail.com");
        memberDto.setPassword("1234");
        memberDto.setPhone("01000000000");
        memberDto.setRole(Role.USER);
        memberDto.setStatus(Status.PUBLIC);

        return memberDto;
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

        MemberDto update = new MemberDto();
        update.setName("테스트3");
        update.setPassword("145");
        update.setStatus(Status.PUBLIC);

        memberService.updateMember(member.getId(),update);

        Member member1 = memberService.findById(id);

        assertEquals(member1.getStatus(), update.getStatus());


    }
}