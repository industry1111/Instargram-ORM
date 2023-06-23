package com.travel.web_oasis.controller;

import com.travel.web_oasis.WebOasisApplication;
import com.travel.web_oasis.domain.posts.Post;
import com.travel.web_oasis.domain.repository.PostRepository;
import com.travel.web_oasis.domain.service.PostService;
import com.travel.web_oasis.domain.service.PostServiceImpl;

import com.travel.web_oasis.web.dto.PostDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
//@WebMvcTest(PostController.class)
@SpringBootTest(classes = WebOasisApplication.class)
class PostControllerTest {
    @InjectMocks
    private PostController postController;

    @Autowired
    private PostServiceImpl postService;

    private MockMvc mockMvc;

    private final List<MultipartFile> files = new ArrayList<>();

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(postController).build();

        String[] fileNames = {"controller.txt", "controller1.txt"};
        for (String fileName: fileNames) {
            MultipartFile file = new MockMultipartFile("file", fileName, "text/plain", fileName.getBytes());
            files.add(file);
        }
    }

    @Test
    void createPost() throws Exception {
        // given
        PostDTO postDTO = PostDTO.builder()
                            .content("test")
                            .build();


        // when
        mockMvc.perform(multipart("/post/create")
                        .file((MockMultipartFile) files.get(0))
                        .file((MockMultipartFile) files.get(1))
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .param("content", postDTO.getContent()))
                .andExpect(status().is3xxRedirection());

        //then
        verify(postService).createPost(postDTO, files);
    }

    @Test
    @DisplayName("게시글 상세보기 요청")
    void getPost() throws Exception {
        //given
        PostDTO postDTO = PostDTO.builder()
                .content("상세보기")
                .build();
        Long expected = postService.createPost(postDTO, files).getId();

        System.out.println("expected = " + expected);

        //when
        mockMvc.perform(get("/post/{id}", expected))
                .andExpect(status().isOk());

        //then
//        verify(postService).getPost(expected);


    }

    @Test
    @DisplayName("게시글삭제 요청")
    void deletePost() throws Exception{

    }
//
//    private PostDTO createMockPostDTO() {
//        MockMultipartFile file = new MockMultipartFile("file", "test1111.txt", "text/plain", "This is a test file.".getBytes());
//        List<MockMultipartFile> files = new ArrayList<>();
//        files.add(file);
//
//    }
}
