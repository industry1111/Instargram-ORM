package com.travel.web_oasis.domain.service;

import com.travel.web_oasis.domain.posts.Post;
import com.travel.web_oasis.web.dto.PostDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class PostServiceTest {
    @Autowired
    private PostService postService;

    @Test
    public void createPost() throws IOException {
        PostDTO postDto = new PostDTO();
        postDto.setContent("This is my first post3.");
        List<MultipartFile> files = new ArrayList<>();
        files.add(new MockMultipartFile("file", "test.txt", "text/plain", new FileInputStream("/Users/gohyeong-gyu/Downloads/logo.png")));

        Post post = postService.createPost(postDto, files);

        assertThat(postDto.getContent()).isEqualTo(post.getContent());
        assertThat(postDto.getFiles().get(0).getFileName()).isEqualTo(post.getFilesAttachList().get(0).getFileName());
    }


}
