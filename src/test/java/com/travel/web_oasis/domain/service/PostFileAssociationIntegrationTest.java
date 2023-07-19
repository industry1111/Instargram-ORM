package com.travel.web_oasis.domain.service;

import com.travel.web_oasis.domain.member.Member;
import com.travel.web_oasis.domain.service.fileAttatch.FileAttachService;
import com.travel.web_oasis.domain.service.post.PostService;
import com.travel.web_oasis.web.dto.FileAttachDTO;
import com.travel.web_oasis.web.dto.PostDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PostFileAssociationIntegrationTest {

    @Autowired
    PostService postService;

    @Autowired
    FileAttachService fileAttachService;


    @Test
    @DisplayName("게시글 생성(파일첨부)")
    @Transactional
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
        List<FileAttachDTO> upload = fileAttachService.upload(files);

        postDto.setFiles(upload);

        Member member = Member.builder()
                .email("aaaa@naver.com")
                .name("aaaa")
                .password("12341q2w")
                .build();
        //when
        Long postId = postService.createPost(postDto,member);

        //then
        PostDTO findPostDTO = postService.findPost(postId);
        String[] fileNames = findPostDTO.getFileNames();

        for (String actual : fileNames) {
            assertThat(actual).isIn((Object[]) expected);
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
        List<FileAttachDTO> upload = fileAttachService.upload(files);

        postDto.setFiles(upload);

        Member member = Member.builder()
                .email("aaaa@naver.com")
                .name("aaaa")
                .password("12341q2w")
                .build();

        Long postId = postService.createPost(postDto,member);

        //when
        postService.deletePost(postId);


        //then
        for (String filename : fileNames) {
            File file = new File(filename);
            assertThat(file.exists()).isFalse();
        }

    }

}
