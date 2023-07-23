package com.travel.web_oasis.domain.repository.follow;

import com.travel.web_oasis.domain.member.Member;
import com.travel.web_oasis.web.dto.MemberDTO;

import java.util.List;

public interface CustomFollowRepository {

    void deleteFollow(Long toMemberId, Long fromMemberId);
    List<MemberDTO> getFollowings(Long memberId);
    List<MemberDTO> getFollowers(Long memberId);
}
