package com.travel.web_oasis.domain.repository;

import java.util.List;

public interface CustomFollowRepository {

    void deleteFollow(Long toMemberId, Long fromMemberId);
    List<Long> getFollowing(Long toMemberId);
    List<Long> getFollower(Long fromMemberId);
}
