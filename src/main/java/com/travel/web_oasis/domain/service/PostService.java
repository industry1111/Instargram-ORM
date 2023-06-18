package com.travel.web_oasis.domain.service;

import com.travel.web_oasis.domain.files.Files;
import com.travel.web_oasis.domain.posts.Post;
import com.travel.web_oasis.domain.repository.FilesRepository;
import com.travel.web_oasis.domain.repository.PostRepository;
import com.travel.web_oasis.web.dto.PostDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    private final FilesRepository filesRepository;

    public Long save(PostDTO postDto) {

        Post post = dtoToEntity(postDto);

        Long id = postRepository.save(post).getId();

        addFilesToPost(post.getFiles());

        return id;
    }

    public void addFilesToPost(List<Files> files) {
        List<Files> savedFiles = filesRepository.saveAll(files);
    }

    public Post dtoToEntity(PostDTO postDto) {
        return Post.builder()
                .content(postDto.getContent())
                .files(postDto.getFiles())
                .build();
    }

    public PostDTO entityToDto(Post post) {
        return PostDTO.builder()
                .id(post.getId())
                .content(post.getContent())
                .files(post.getFiles())
                .createdDate(post.getCreatedDate().toString())
                .modifiedDate(post.getModifiedDate().toString())
                .build();
    }

    public Post findById(Long id) {

        return postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다. id : " + id)
        );
    }

    public PostDTO createPost(PostDTO postDTO) {

        Post post = dtoToEntity(postDTO);

        Long saveId = postRepository.save(post).getId();

        return null;
    }
}

