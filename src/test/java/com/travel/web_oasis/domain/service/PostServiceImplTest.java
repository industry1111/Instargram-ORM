package com.travel.web_oasis.domain.service;

import com.travel.web_oasis.domain.posts.Post;
import com.travel.web_oasis.web.dto.PostDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class PostServiceImplTest {
    @Autowired
    private PostService postService;

    @Test
    @DisplayName("게시글 생성")
    public void createPost() throws IOException {
        //given
        PostDTO postDto = new PostDTO();
        postDto.setContent("This is my first post3.");
        MultipartFile[] files = new MultipartFile[1];
        files[0] = new MockMultipartFile("file", "test.txt", "text/plain", new FileInputStream("/Users/gohyeong-gyu/Downloads/logo.png"));

        //when
//        Post post = postService.createPost(postDto, files);

        //then
//        assertThat(postDto.getContent()).isEqualTo(post.getContent());
//        assertThat(postDto.getFiles().get(0).getFileName()).isEqualTo(post.getFileAttachList().get(0).getFileName());
    }

    @Test
    @DisplayName("게시글 찾기")
    public void findById() throws IOException{
        //given
        PostDTO postDto = new PostDTO();
        postDto.setContent("찾기용 게시글");
        MultipartFile[] files = new MultipartFile[1];
        files[0] = new MockMultipartFile("file", "test.txt", "text/plain", new FileInputStream("/Users/gohyeong-gyu/Downloads/logo.png"));

//        Long id = postService.createPost(postDto, files).getId();

        //when
//        Post findPost = postService.findById(id);

        //then
//        assertThat(findPost.getId()).isEqualTo(id);
//        assertThat(findPost.getContent()).isEqualTo("찾기용 게시글");

    }

    @Test
    @DisplayName("게시글 삭제")
    void deletePost() throws IOException{
        //given
        PostDTO postDTO = PostDTO.builder()
                .content("삭제용 게시글")
                .build();

        MultipartFile[] files = new MultipartFile[1];
        files[0] = new MockMultipartFile("file", "test.txt", "text/plain", new FileInputStream("/Users/gohyeong-gyu/Downloads/logo.png"));

        try {
            //when
//            Long savePostId = postService.createPost(postDTO, files).getId();
//            postService.deletePost(savePostId);
        } catch (IllegalArgumentException e) {
            // Then
//            assertThat(e).isInstanceOf(IllegalArgumentException.class);
//            assertThat(e.getMessage()).isEqualTo("해당 게시글이 없습니다. id : 1");
        }
    }
}
