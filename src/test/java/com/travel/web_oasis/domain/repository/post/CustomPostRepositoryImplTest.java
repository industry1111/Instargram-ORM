package com.travel.web_oasis.domain.repository.post;

import com.travel.web_oasis.domain.entity.Post;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomPostRepositoryImplTest {

    @Autowired
    private PostRepository postRepository;

    @Test
    @Transactional
    void getMemberPostList() {

        Long memberId = 2L;

        Pageable pageable = PageRequest.of(0,3);

        Page<Post> result = postRepository.getMemberPostList(pageable,memberId);

        result.stream().forEach(post -> System.out.println("post = " + post));
    }
}