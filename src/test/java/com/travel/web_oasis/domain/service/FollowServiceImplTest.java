package com.travel.web_oasis.domain.service;

import com.travel.web_oasis.web.dto.MemberDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class FollowServiceImplTest {

    @Autowired
    private  MemberService memberService;

    @Autowired
    private  FollowService followService;

    @Test
    void followTest() {

        //given
        MemberDTO memberDTO = MemberDTO.builder()
                .email("Test계정1@Test.com")
                .password("1234")
                .name("TEST계정")
                .build();

        MemberDTO memberDTO2 = MemberDTO.builder()
                .email("Test계정2@Test.com")
                .password("1234")
                .name("TEST계정2")
                .build();

        Long toMemberId = memberService.saveMember(memberDTO);
        Long fromMemberId = memberService.saveMember(memberDTO2);

        Long expected = 1L;

        //when
        Long actual = followService.followMember(toMemberId,fromMemberId);

        //then
        assertThat(actual).isEqualTo(expected);

    }

    @Test
    void unFollowMember() {
        MemberDTO memberDTO = MemberDTO.builder()
                .email("Test계정3@Test.com")
                .password("1234")
                .name("TEST계정")
                .build();

        MemberDTO memberDTO2 = MemberDTO.builder()
                .email("Test계정4@Test.com")
                .password("1234")
                .name("TEST계정2")
                .build();

        Long toMemberId = memberService.saveMember(memberDTO);
        Long fromMemberId = memberService.saveMember(memberDTO2);

        Long id = followService.followMember(toMemberId,fromMemberId);

        //when
        followService.unFollowMember(toMemberId,fromMemberId);

        //then

    }
}