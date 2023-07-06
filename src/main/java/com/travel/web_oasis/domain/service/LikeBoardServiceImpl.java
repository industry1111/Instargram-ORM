package com.travel.web_oasis.domain.service;

import com.travel.web_oasis.domain.entity.LikeBoard;
import com.travel.web_oasis.domain.entity.Post;
import com.travel.web_oasis.domain.member.Member;
import com.travel.web_oasis.domain.repository.LikeBoardRepository;
import com.travel.web_oasis.web.dto.LikeBoardDTO;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeBoardServiceImpl implements LikeBoardService{
    private final LikeBoardRepository likeBoardRepository;
    private final MemberService memberService;
    private final PostService postService;

    Logger logger = LoggerFactory.getLogger(LikeBoardServiceImpl.class);

    @Override
    public Long addLikeBoard(Long postId, Long memberId) {
        Member member = memberService.findById(memberId);
        Post post = postService.findById(postId);

        if (member == null || post == null) {
            logger.info("member or post = null");
            return -1L;
        }
        if (findByMemberAndPost(member,post) != null) {
            deleteLikeBoard(member, post);
            return 0L;
        }
        LikeBoard likeBoard = LikeBoard.builder()
                .member(member)
                .post(post)
                .build();
        return likeBoardRepository.save(likeBoard).getId();
    }


    @Override
    public void deleteLikeBoard(Member member, Post post) {
        likeBoardRepository.deleteByMemberAndPost(member,post);
    }

    @Override
    public LikeBoard findByMemberAndPost(Member member, Post post) {
        return likeBoardRepository.findByMemberAndPost(member, post);
    }

    @Override
    public List<LikeBoard> findByMember(Long memberId) {
        Member member = memberService.findById(memberId);

        return likeBoardRepository.findByMember(member);
    }
}
