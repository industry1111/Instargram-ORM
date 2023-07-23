package com.travel.web_oasis.domain.service.follow;

import com.travel.web_oasis.domain.entity.Follow;
import com.travel.web_oasis.domain.member.Member;
import com.travel.web_oasis.domain.repository.follow.FollowRepository;
import com.travel.web_oasis.domain.service.member.MemberService;
import com.travel.web_oasis.web.dto.FollowDTO;
import com.travel.web_oasis.web.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FollowServiceImpl implements FollowService{

    private final FollowRepository followRepository;
    private final MemberService memberService;

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
    public List<MemberDTO> getFollowingList(Long memberId) {
        return followRepository.getFollowings(memberId);
    }

    @Override
    public List<MemberDTO> getFollowerList(Long memberId) {
        return followRepository.getFollowers(memberId);
    }
}
