package com.travel.web_oasis.domain.service.follow;

import com.travel.web_oasis.web.dto.FollowDTO;
import com.travel.web_oasis.web.dto.MemberDTO;

import java.util.List;

public interface FollowService {
    Long followMember(Long toMemberId, Long fromMemberId);
    void unFollowMember(Long toMemberId, Long fromMemberId);
    List<MemberDTO> getFollowingList(Long memberId);
    List<MemberDTO> getFollowerList(Long memberId);
}
