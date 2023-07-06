package com.travel.web_oasis.domain.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
class FollowRepositoryImplTest {

    @Autowired
    private FollowRepository followRepository;

    @Test
    void getFollowingCnt() {
        //given
        Long toMemberId = 1L;
        Long expected = 0L;

        //when
        Long actual = followRepository.getFollowingCnt(toMemberId);

        //then
        assertThat(actual).isEqualTo(expected);
    }



    @Test
    void getFollowerCnt() {
        //given
        Long fromMemberId = 1L;
        Long expected = 0L;

        //when
        Long actual = followRepository.getFollowerCnt(fromMemberId);

        //then
        assertThat(actual).isEqualTo(expected);
    }
}