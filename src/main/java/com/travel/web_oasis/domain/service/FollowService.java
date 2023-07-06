package com.travel.web_oasis.domain.service;

import com.travel.web_oasis.web.dto.FollowDTO;

public interface FollowService {
    Long followMember(Long toMemberId, Long fromMemberId);
    void unFollowMember(Long toMemberId, Long fromMemberId);
    FollowDTO getFollow(Long memberId);
}
