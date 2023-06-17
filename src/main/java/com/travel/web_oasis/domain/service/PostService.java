package com.travel.web_oasis.domain.service;

import com.travel.web_oasis.domain.posts.Post;
import com.travel.web_oasis.domain.repository.PostRepository;
import com.travel.web_oasis.web.dto.PostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;

    public PostResponseDto findById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow( () -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        return new PostResponseDto(post);
    }

}
