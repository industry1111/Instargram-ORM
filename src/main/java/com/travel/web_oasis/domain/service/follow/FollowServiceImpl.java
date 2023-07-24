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
    public String unFollowMember(Long toMemberId, Long fromMemberId) {

        String result = "관리자에게 문의해 주세요";
        if (followRepository.deleteFollow(toMemberId, fromMemberId) != null) {
            result =  "정상적으로 언팔로우 됐습니다.";
        }
        return result;
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
