package com.travel.web_oasis.domain.service;

import com.travel.web_oasis.domain.files.Files;
import com.travel.web_oasis.web.dto.PostDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class PostServiceTest {

    @Autowired
    PostService postService;

    @Test
    void savePost() {
        List<Files> files = new ArrayList<>();

        for(int i = 0; i < 10; i++) {
            Files file = Files.builder()
                    .fileName("test"+i)
                    .filePath("testPath"+i)
                    .build();
            files.add(file);
        }

        PostDTO postDto = PostDTO.builder()
                .content("test1")
                .files(files)
                .build();

        Long id = postService.save(postDto);

        assertThat(id).isEqualTo(1L);
    }

}