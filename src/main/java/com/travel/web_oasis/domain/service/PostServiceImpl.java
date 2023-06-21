package com.travel.web_oasis.domain.service;

import com.travel.web_oasis.domain.files.FilesAttach;
import com.travel.web_oasis.domain.posts.Post;
import com.travel.web_oasis.domain.repository.PostRepository;
import com.travel.web_oasis.web.dto.PostDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;

    @Override
    public Post createPost(PostDTO postDTO, MultipartFile[] files) {
        String fileUrl = "http://localhost:8080/files/";

        List<FilesAttach> filesAttachList = new ArrayList<>();

        for (MultipartFile multipartFile : files) {
            FilesAttach filesAttach = FilesAttach.builder()
                    .fileName(multipartFile.getOriginalFilename())
                    .fileUrl(fileUrl + multipartFile.getOriginalFilename())
                    .build();
            filesAttachList.add(filesAttach);
        }

        postDTO.setFiles(filesAttachList);

        Post post = dtoToEntity(postDTO);

        return postRepository.save(post);
    }

    @Override
    public Post findById(Long id) {
        return postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다. id : " + id)
        );
    }
    public Post dtoToEntity(PostDTO postDto) {
        return Post.builder()
                .content(postDto.getContent())
                .filesAttachList(postDto.getFiles())
                .build();
    }

    public PostDTO entityToDto(Post post) {
        return PostDTO.builder()
                .id(post.getId())
                .content(post.getContent())
                .files(post.getFilesAttachList())
                .createdDate(post.getCreatedDate().toString())
                .modifiedDate(post.getModifiedDate().toString())
                .build();
    }


}
