package com.travel.web_oasis.domain.service;

import com.travel.web_oasis.web.dto.PostDTO;
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


    @Test
    @DisplayName("게시글 생성")
    public void createPost() throws IOException {
        //given
        Long expected = 1L ;
        PostDTO postDto = new PostDTO();
        postDto.setContent("게시글 생성 테스트");

        List<MultipartFile> files = new ArrayList<>();

        //when
        Long actual = postService.createPost(postDto, files);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("게시글 찾기")
    public void getPost() throws IOException{
        //given
        String expected = "게시글 찾기 테스트";
        PostDTO postDto = new PostDTO();
        postDto.setContent(expected);

        List<MultipartFile> files = new ArrayList<>();

        //when
        Long id = postService.createPost(postDto, files);

        PostDTO findPostDTO = postService.findPost(id);
        String actual = findPostDTO.getContent();

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("게시글 삭제")
    void deletePost() throws IOException{
        //given
        PostDTO postDTO = PostDTO.builder()
                .content("삭제용 게시글")
                .build();

        List<MultipartFile> files = new ArrayList<>();
        Long savePostId = postService.createPost(postDTO, files);
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
}
