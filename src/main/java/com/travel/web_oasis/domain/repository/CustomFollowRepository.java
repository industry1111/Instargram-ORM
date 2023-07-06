package com.travel.web_oasis.domain.repository;

import com.travel.web_oasis.domain.entity.Follow;
import com.travel.web_oasis.domain.member.Member;

public interface CustomFollowRepository {
    void deleteFollow(Long toMemberId, Long fromMemberId);
    Long getFollowingCnt(Long toMemberId);
    Long getFollowerCnt(Long fromMemberId);
}
