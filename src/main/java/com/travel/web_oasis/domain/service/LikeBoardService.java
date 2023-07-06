package com.travel.web_oasis.domain.service;

import com.travel.web_oasis.domain.entity.LikeBoard;
import com.travel.web_oasis.domain.entity.Post;
import com.travel.web_oasis.domain.member.Member;
import com.travel.web_oasis.web.dto.LikeBoardDTO;

import java.util.List;

public interface LikeBoardService {
    Long addLikeBoard(Long postId, Long memberId);

    void deleteLikeBoard(Member member, Post post);


    LikeBoard findByMemberAndPost(Member member, Post post);

    List<LikeBoard> findByMember(Long memberId);

    default LikeBoardDTO entityToDto(LikeBoard likeBoard) {
        return LikeBoardDTO.builder()
                .memberId(likeBoard.getMember().getId())
                .postId(likeBoard.getPost().getId())
                .build();
    }

}
