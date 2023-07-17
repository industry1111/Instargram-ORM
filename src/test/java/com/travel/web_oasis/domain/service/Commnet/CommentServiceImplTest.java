package com.travel.web_oasis.domain.service.Commnet;

import com.travel.web_oasis.domain.entity.Comment;
import com.travel.web_oasis.domain.entity.Post;
import com.travel.web_oasis.domain.member.Member;
import com.travel.web_oasis.domain.member.Role;
import com.travel.web_oasis.domain.member.Status;
import com.travel.web_oasis.domain.repository.member.MemberRepository;
import com.travel.web_oasis.domain.repository.post.PostRepository;
import com.travel.web_oasis.web.dto.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
class CommentServiceImplTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void setUp() {
        Member member = Member.builder()
                .name("테스트멤버")
                .email("TestMail@test.com")
                .password("1234")
                .status(Status.PUBLIC)
                .role(Role.USER)
                .build();

        memberRepository.save(member);

        Post post = Post.builder()
                .content("게시글 생성")
                .fileAttachList(new ArrayList<>())
                .member(member)
                .build();

        postRepository.save(post);
    }
    @Test
    @DisplayName("댓글 등록")
    @Transactional
    void addComment() {
        //given
        Long memberId = 1L;
        Long postId = 1L;

        CommentDTO commentDTO = CommentDTO.builder()
                .content("댓글")
                .memberId(memberId)
                .postId(postId)
                .build();

        //when
        //then
        assertThatNoException().isThrownBy(() -> commentService.addComment(commentDTO));
    }

    @Test
    @DisplayName("댓글 가져오기")
    @Transactional
    void getCommentList() {
        //given
        Long postId = 1L;
        Long memberId = 1L;

        CommentDTO commentDTO = CommentDTO.builder()
                .content("댓글")
                .memberId(memberId)
                .postId(postId)
                .build();

        commentService.addComment(commentDTO);


        //when
        commentService.getCommentList(postId);

        //then
//        String content = result.getDtoList().get(0).getContent();
//        assertThat(content).isEqualTo("댓글");
    }
}