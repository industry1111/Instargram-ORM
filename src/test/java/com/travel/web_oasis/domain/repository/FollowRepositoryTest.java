package com.travel.web_oasis.domain.repository;

import com.travel.web_oasis.domain.member.Follow;
import com.travel.web_oasis.domain.member.Member;
import com.travel.web_oasis.domain.member.Role;
import com.travel.web_oasis.domain.member.Status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FollowRepositoryTest {

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private MemberRepository memberRepository;
    @Test
    void followTest() {

        Member member1 = Member.builder()
                .email("industr1111@naver.com")
                .name("고형규")
                .password("1q2w3e4r")
                .role(Role.USER)
                .status(Status.PUBLIC)
                .provider("local")
                .build();

        Member member2 = Member.builder()
                .email("qawsedrfnnn@google.com")
                .name("김민호")
                .password("1q2w3e4r")
                .role(Role.USER)
                .status(Status.PUBLIC)
                .provider("local")
                .build();

        memberRepository.save(member1);
        memberRepository.save(member2);

        Follow follow = Follow.builder()
                .fromMember(member1)
                .toMember(member2)
                .build();

        followRepository.save(follow);

        Optional<Follow> result = followRepository.findById(1L);

        System.out.println("result = " + result.get());
    }
}