package com.travel.web_oasis.domain.repository;

import com.travel.web_oasis.domain.entity.LikeBoard;
import com.travel.web_oasis.domain.entity.Post;
import com.travel.web_oasis.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeBoardRepository extends JpaRepository<LikeBoard, Long> {

    LikeBoard findByMemberAndPost(Member member, Post post);

    void deleteByMemberAndPost(Member member, Post post);

    List<LikeBoard> findByMember(Member member);
}
