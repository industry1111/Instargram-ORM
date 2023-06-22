package com.travel.web_oasis.controller;

import com.travel.web_oasis.domain.posts.Post;
import com.travel.web_oasis.domain.repository.PostRepository;
import com.travel.web_oasis.domain.service.PostServiceImpl;

import com.travel.web_oasis.web.dto.PostDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PostController.class)
class PostControllerTest {
    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostController postController;

    @MockBean
    private PostServiceImpl postService;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(postController).build();
    }

    @Test
    void createPost() throws Exception {
        // given


        // when && then
        mockMvc.perform(multipart("/post/create")
//                        .file(files.get(0))
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("게시글 상세보기 요청")
    void getPost() throws Exception {
        //given
//
//        //when && then
//        mockMvc.perform(get("/post/{id}", 1L))
//                .andExpect(status().isOk();
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
