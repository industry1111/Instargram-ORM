package com.travel.web_oasis.domain.service;

import com.travel.web_oasis.domain.files.FileAttach;
import com.travel.web_oasis.domain.posts.Post;
import com.travel.web_oasis.web.dto.PostDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PostFileAssociationIntegrationTest {

    @Autowired
    PostService postService;

    @Autowired
    FileAttachService fileAttachService;


    @Test
    @DisplayName("게시글 생성(파일첨부)")
    void createPostWithFile() {
        //given
        PostDTO postDto = new PostDTO();
        postDto.setContent("게시글 생성 테스트");

        List<MultipartFile> files = new ArrayList<>();
        String[] expected = {"test0.txt", "test1.txt", "test2.txt", "test3.txt", "test4.txt"};
        for (String str: expected) {
            MultipartFile file = new MockMultipartFile("file", str, "text/plain", str.getBytes());
            files.add(file);
        }
        Post post = postService.createPost(postDto, files);

        //when
        PostDTO findPostDTO = postService.getPost(post.getId());
        List<FileAttach> fileAttaches = findPostDTO.getFiles();

        //then
        for (FileAttach file : fileAttaches) {
            assertThat(file.getFileName()).isIn((Object[]) expected);
        }
    }

    @Test
    @DisplayName("게시글 및 첨부 파일 삭제")
    @Transactional
    void deletePostWithFile() {
        //given
        PostDTO postDto = new PostDTO();
        postDto.setContent("게시글 삭제 테스트");

        List<MultipartFile> files = new ArrayList<>();
        String[] fileNames = {"deleteTest0.txt", "deleteTest1.txt", "deleteTest2.txt", "deleteTest3.txt", "deleteTest4.txt"};
        for (String fileName: fileNames) {
            MultipartFile file = new MockMultipartFile("file", fileName, "text/plain", fileName.getBytes());
            files.add(file);
        }
        Post post = postService.createPost(postDto, files);

        List<FileAttach> fileAttaches = post.getFileAttachList();
        List<String> expected = new ArrayList<>();
        for (FileAttach fileAttach : fileAttaches) {
            expected.add(fileAttach.getFileStoreName());
        }

        System.out.println("before post = " + post.getId());
        //when
        postService.deletePost(post.getId());

        System.out.println("post = " + post.getId());

        //then
        for (String storageName : expected) {
            File file = new File(storageName);
            assertThat(file.exists()).isFalse();
        }

    }

}
