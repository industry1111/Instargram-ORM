//package com.travel.web_oasis.domain.service;
//
//import com.travel.web_oasis.domain.posts.Post;
//import com.travel.web_oasis.web.dto.PostDTO;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.*;
//
//@SpringBootTest
//class PostServiceImplTest {
//    @Autowired
//    private PostService postService;
//
//
//    @Test
//    @DisplayName("게시글 생성")
//    public void createPost() throws IOException {
//        //given
//        String expected = "게시글 생성 테스트";
//        PostDTO postDto = new PostDTO();
//        postDto.setContent(expected);
//
//        List<MultipartFile> files = new ArrayList<>();
//
//        //when
//        Post post = postService.createPost(postDto, files);
//        String actual = post.getContent();
//
//        //then
//        assertThat(actual).isEqualTo(expected);
//    }
//
//    @Test
//    @DisplayName("게시글 찾기")
//    public void getPost() throws IOException{
//        //given
//        String expected = "게시글 찾기 테스트";
//        PostDTO postDto = new PostDTO();
//        postDto.setContent(expected);
//
//        List<MultipartFile> files = new ArrayList<>();
//
//        //when
//        Post post = postService.createPost(postDto, files);
//
//        PostDTO findPostDTO = postService.getPost(post.getId());
//        String actual = findPostDTO.getContent();
//
//        //then
//        assertThat(actual).isEqualTo(expected);
//    }
//
//    @Test
//    @DisplayName("게시글 삭제")
//    void deletePost() throws IOException{
//        //given
//        PostDTO postDTO = PostDTO.builder()
//                .content("삭제용 게시글")
//                .build();
//
//        List<MultipartFile> files = new ArrayList<>();
//        Long savePostId = postService.createPost(postDTO, files).getId();
//        String expected = "해당 게시글이 없습니다. id : "+savePostId;
//
//        try {
//            //when
//            postService.deletePost(savePostId);
//        } catch (IllegalArgumentException e) {
//            // Then
//            assertThat(e).isInstanceOf(IllegalArgumentException.class);
//            assertThat(e.getMessage()).isEqualTo(expected);
//        }
//    }
//}
