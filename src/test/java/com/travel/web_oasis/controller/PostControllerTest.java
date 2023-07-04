package com.travel.web_oasis.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.travel.web_oasis.WebOasisApplication;
import com.travel.web_oasis.domain.files.FileAttach;
import com.travel.web_oasis.domain.posts.Post;
import com.travel.web_oasis.domain.service.PostServiceImpl;

import com.travel.web_oasis.web.dto.PostDTO;
import org.assertj.core.api.Assertions;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
//@WebMvcTest(PostController.class)
@SpringBootTest(classes = WebOasisApplication.class)
class PostControllerTest {

    @Autowired
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
        // when && then
        // 리다이렉션이 발생했는지 확인
        mockMvc.perform(multipart("/post/create")
                        .file((MockMultipartFile) files.get(0))
                        .file((MockMultipartFile) files.get(1))
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .param("content", postDTO.getContent()))
                .andExpect(status().is3xxRedirection());

        //then
//        verify(postService).createPost(postDTO, files);
    }

    @Test
    @DisplayName("게시글 상세보기 요청")
    @Transactional
    void getPost() throws Exception {
        //given
       PostDTO postDTO = PostDTO.builder()
                            .content("게시글 상세보기 요청 테스트")
                            .build();
        Long id = postService.createPost(postDTO,null);

        //when && then
        //리턴값에 JSON 형식으로 post id, content 있는지 확인
        //리턴값에 JSON Array 형식으로 files 있는지 확인
        MvcResult mvcResult = mockMvc.perform(get("/post/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.content").value("게시글 상세보기 요청 테스트"))
//                .andExpect(jsonPath("$.files").isArray())
                .andReturn();

//        String responseBody = mvcResult.getResponse().getContentAsString();
//        JSONObject jsonObject = new JSONObject(responseBody);
    }

//    @Test
//    @DisplayName("게시글삭제 요청")
//    void deletePost() throws Exception{
//        //given
//        PostDTO postDTO = PostDTO.builder()
//                .content("게시글 삭제 요청 테스트")
//                .build();
//
//        Post post = postService.createPost(postDTO, files);
//        //파일 저장경로 리스트 생성
//        List<String> fileStoreNames = post.getFileAttachList().stream()
//                .map(FileAttach::getFileStoreName)
//                .toList();
//
//        mockMvc.perform(delete("/post/delete/{id}", post.getId()))
//                .andExpect(status().isOk());
//
//
//        //when && then
//        //삭제된 게시글이 조회되지 않는지 확인
//        assertThat(postService.getPost(post.getId())).isNull();
//
//        //저장했던 파일이 저장경로에 파일이 존재하지 않는지 확인
//        for (String fileStoreName: fileStoreNames) {
//            File file = new File(fileStoreName);
//            assertThat(file.exists()).isFalse();
//        }
//
//    }

}
