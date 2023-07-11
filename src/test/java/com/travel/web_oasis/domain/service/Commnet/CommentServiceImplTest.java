package com.travel.web_oasis.domain.service.Commnet;

import com.travel.web_oasis.domain.entity.Comment;
import com.travel.web_oasis.domain.entity.Post;
import com.travel.web_oasis.domain.member.Member;
import com.travel.web_oasis.domain.member.Role;
import com.travel.web_oasis.domain.member.Status;
import com.travel.web_oasis.domain.repository.member.MemberRepository;
import com.travel.web_oasis.domain.repository.post.PostRepository;
import com.travel.web_oasis.domain.service.PostService;
import com.travel.web_oasis.web.dto.MemberDTO;
import com.travel.web_oasis.web.dto.PostDTO;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class CommentServiceImplTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PostRepository postRepository;

    @Test
    @DisplayName("댓글 등록")
//    @Transactional
    void addComment() {
        //given
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

        CommentDTO commentDTO = CommentDTO.builder()
                .content("댓글")
                .memberId(member.getId())
                .postId(post.getId())
                .build();

        //when
        //then
        assertThatNoException().isThrownBy(() -> commentService.addComment(commentDTO));
    }
}