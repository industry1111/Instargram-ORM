package com.travel.web_oasis.domain.service;

import com.travel.web_oasis.domain.files.FileAttach;
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
    public Post createPost(PostDTO postDTO) {
        String fileUrl = "http://localhost:8080/files/";

        List<FileAttach> fileAttachList = new ArrayList<>();

        for (MultipartFile multipartFile : postDTO.getFiles()) {
            FileAttach fileAttach = FileAttach.builder()
                    .fileName(multipartFile.getOriginalFilename())
                    .fileUrl(fileUrl + multipartFile.getOriginalFilename())
                    .build();
            fileAttachList.add(fileAttach);
        }

//        postDTO.setFiles(fileAttachList);

        Post post = dtoToEntity(postDTO);

        return postRepository.save(post);
    }

    @Override
    public Post findById(Long id) {
        return postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다. id : " + id)
        );
    }

    @Override
    public void deletePost(Long id) {
        Post removePost = findById(id);
        postRepository.delete(removePost);
    }

    public Post dtoToEntity(PostDTO postDto) {
        return Post.builder()
                .content(postDto.getContent())
//                .filesAttachList(postDto.getFiles())
                .build();
    }

    public PostDTO entityToDto(Post post) {
        return PostDTO.builder()
                .id(post.getId())
                .content(post.getContent())
//                .files(post.getFileAttachList())
                .createdDate(post.getCreatedDate().toString())
                .modifiedDate(post.getModifiedDate().toString())
                .build();
    }


}
