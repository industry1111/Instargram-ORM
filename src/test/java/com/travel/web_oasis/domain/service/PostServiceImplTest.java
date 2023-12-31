package com.travel.web_oasis.domain.service;

import com.travel.web_oasis.domain.member.Member;
import com.travel.web_oasis.domain.member.Role;
import com.travel.web_oasis.domain.member.Status;
import com.travel.web_oasis.domain.entity.Post;
import com.travel.web_oasis.domain.service.member.MemberService;
import com.travel.web_oasis.domain.service.post.PostService;
import com.travel.web_oasis.web.dto.MemberDTO;
import com.travel.web_oasis.web.dto.PageRequestDTO;
import com.travel.web_oasis.web.dto.PageResultDTO;
import com.travel.web_oasis.web.dto.PostDTO;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class PostServiceImplTest {
    @Autowired
    private PostService postService;

    @Autowired
    private MemberService memberService;

    MemberDTO memberDTO;

    @BeforeEach
    void setUp() {
           memberDTO = MemberDTO.builder()
                   .name("테스트")
                   .email("게시글test@mail.com")
                   .password("1234")
                   .role(Role.USER)
                   .status(Status.PUBLIC)
                   .build();
    }

    @Test
    @DisplayName("게시글 생성")
    @Transactional
    public void createPost() throws IOException {
        //given
        PostDTO postDto = new PostDTO();
        postDto.setContent("게시글 생성 테스트");

        List<MultipartFile> files = new ArrayList<>();

        Long id = memberService.saveMember(memberDTO);

        Member member = memberService.findById(id);

        //when
        Long actual = postService.createPost(postDto, member);

        //then
        assertThat(actual).isNotNull();
    }

    @Test
    @DisplayName("게시글 찾기")
    @Transactional
    public void getPost() throws IOException{
        //given
        String expected = "게시글 찾기 테스트";
        PostDTO postDto = new PostDTO();
        postDto.setContent(expected);

        Long id = memberService.saveMember(memberDTO);

        Member member = memberService.findById(id);


        //when
        Long postId = postService.createPost(postDto, member);

        PostDTO findPostDTO = postService.findPost(postId);
        String actual = findPostDTO.getContent();

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("게시글 삭제")
    @Transactional
    void deletePost() throws IOException{
        //given
        PostDTO postDTO = PostDTO.builder()
                .content("삭제용 게시글")
                .build();

        Long id = memberService.saveMember(memberDTO);

        Member member = memberService.findById(id);

        //when
        Long postId = postService.createPost(postDTO, member);
        Long savePostId = postService.createPost(postDTO, member);
        String expected = "해당 게시글이 없습니다. id : "+savePostId;

        try {
            //when
            postService.deletePost(savePostId);
        } catch (IllegalArgumentException e) {
            // Then
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
            assertThat(e.getMessage()).isEqualTo(expected);
        }
    }


    @Test
    @DisplayName("게시글 목록 가져오기(Paging)")
    @Transactional
    void getList() {

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                                                    .page(2)
                                                    .size(1)
                                                    .build();


        PageResultDTO<PostDTO, Post> resultDTO = postService.getPostList(pageRequestDTO,2L);

        for (PostDTO postDTO : resultDTO.getDtoList()) {
            System.out.println("postDTO = " + postDTO);
        }
    }

    @Test
    @DisplayName("게시글 목록 가져오기(Paging)")
    @Transactional
    void getMemberPostList() {

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(3)
                .build();


        PageResultDTO<PostDTO, Post> resultDTO = postService.getMemberPostList(pageRequestDTO,1L);

        System.out.println("resultDTO = " + resultDTO.getPrev());
        System.out.println("resultDTO.getNext() = " + resultDTO.getNext());
        System.out.println("resultDTO = " + resultDTO.getTotalPage());
        for (PostDTO postDTO : resultDTO.getDtoList()) {
            System.out.println("postDTO = " + postDTO);
        }

        System.out.println("resultDTO.getPage() = " + resultDTO.getPageList());
    }
}
