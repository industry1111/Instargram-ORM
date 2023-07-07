package com.travel.web_oasis.domain.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
class FollowRepositoryImplTest {

    @Autowired
    private FollowRepository followRepository;

    @Test
    void getFollowingCnt() {
        //given
        Long toMemberId = 1L;

        List<Long> expected = new ArrayList<>();

        //when
        List<Long> actual = followRepository.getFollowing(toMemberId);

        //then
        assertThat(actual).isIn(expected);
    }



    @Test
    void getFollowerCnt() {
        //given
        Long fromMemberId = 1L;
        List<Long> expected = new ArrayList<>();

        //when
        List<Long> actual = followRepository.getFollower(fromMemberId);

        //then
        assertThat(actual).isIn(expected);
    }
}