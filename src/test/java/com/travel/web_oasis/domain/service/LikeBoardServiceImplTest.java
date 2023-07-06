package com.travel.web_oasis.domain.service;

import com.travel.web_oasis.domain.entity.LikeBoard;
import com.travel.web_oasis.domain.entity.Post;
import com.travel.web_oasis.domain.member.Member;
import com.travel.web_oasis.domain.member.Role;
import com.travel.web_oasis.domain.member.Status;
import com.travel.web_oasis.web.dto.LikeBoardDTO;
import com.travel.web_oasis.web.dto.MemberDTO;
import com.travel.web_oasis.web.dto.PostDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class LikeBoardServiceImplTest {

    @Autowired
    LikeBoardServiceImpl likeService;

    @Autowired
    MemberServiceImpl memberService;

    @Autowired
    PostServiceImpl postService;

    Long testMember() {
        MemberDTO memberDTO = MemberDTO.builder()
                .name("테스트")
                .email("test113@mail.com")
                .password("1234")
                .role(Role.USER)
                .status(Status.PUBLIC)
                .provider("local")
                .build();

        return memberService.saveMember(memberDTO);
    }

    Long testPost() {
        PostDTO postDTO = PostDTO.builder()
                .content("safasf")
                .build();
        testMember();
        Member member = memberService.findByEmailAndProvider("test113@mail.com", "local");

        return postService.createPost(postDTO, member);
    }

//    @Test
//    void addLike() {
//        //given
//        Long memberId = testMember();
//        Long postId = testPost();
//        //when
//        likeService.addLikeBoard(postId,memberId);
//        //then
//        Member member = memberService.findById(memberId);
//        Post post = postService.findById(postId);
//
//        LikeBoard resultLikeBoard = likeService.findByMemberAndPost(member, post);
//
//       assertThat(resultLikeBoard.getMember().getId().equals(memberId));
//       assertThat(resultLikeBoard.getPost().getId().equals(postId));
//    }

    @Test
    void deleteLikeBoard() {
    }
}