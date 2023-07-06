package com.travel.web_oasis.domain.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.support.QueryBase;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.travel.web_oasis.domain.entity.Follow;
import com.travel.web_oasis.domain.entity.QFollow;
import com.travel.web_oasis.domain.member.Member;
import com.travel.web_oasis.domain.repository.FollowRepository;
import com.travel.web_oasis.web.dto.FollowDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FollowServiceImpl implements FollowService{

    private final FollowRepository followRepository;
    private final MemberService memberService;
    private final EntityManagerFactory emf;
    @Override
    public Long followMember(Long toMemberId, Long fromMemberId) {

        Member toMember = memberService.findById(toMemberId);
        Member fromMember = memberService.findById(fromMemberId);

        Follow follow = Follow.builder()
                .toMember(toMember)
                .fromMember(fromMember)
                .build();

        return followRepository.save(follow).getId();
    }

    @Override
    public void unFollowMember(Long toMemberId, Long fromMemberId) {

        followRepository.deleteFollow(toMemberId, fromMemberId);

    }

    @Override
    public FollowDTO getFollow(Long memberId) {

//        followRepository.getFollowingCnt()

        return null;
    }
}
