package com.travel.web_oasis.domain.service;

import com.travel.web_oasis.domain.service.follow.FollowService;
import com.travel.web_oasis.domain.service.member.MemberService;
import com.travel.web_oasis.web.dto.MemberDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class FollowServiceImplTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private FollowService followService;

    @Test
    @Transactional
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



        //when
        Long actual = followService.followMember(toMemberId,fromMemberId);

        //then
        assertThat(actual).isNotNull();

    }

    @Test
    @Transactional
    void unFollowMember() {

        //given
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
        String actual = followService.unFollowMember(toMemberId,fromMemberId);

        //then
        assertThat(actual).isEqualTo("정상적으로 언팔로우 됐습니다.");
    }
}