package com.travel.web_oasis.domain.service;

import com.travel.web_oasis.domain.files.FileAttach;
import com.travel.web_oasis.domain.posts.Post;
import com.travel.web_oasis.domain.repository.PostRepository;
import com.travel.web_oasis.web.dto.PostDTO;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;

    private final FileAttachService fileAttachService;

    private final Logger log = org.slf4j.LoggerFactory.getLogger(PostServiceImpl.class);

    @Override
    public Post createPost(PostDTO postDTO, List<MultipartFile> files) {

        log.info("createPost() called   PostDTO : {}", postDTO);
        List<FileAttach> fileAttachList = fileAttachService.upload(files);

        postDTO.setFiles(fileAttachList);

        Post post = dtoToEntity(postDTO);

        return postRepository.save(post);
    }

    @Override
    public Post getPost(Long id) {
        return postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다. id : " + id)
        );
    }

    @Override
    public void deletePost(Long id) {
        //삭제할 포스트
        Post removePost = getPost(id);
        //삭제할 포스트의 첨부파일들
        List<FileAttach> files = removePost.getFileAttachList();
        //저장소에서 첨부파일들 삭제
        fileAttachService.deleteFiles(files);
        //포스트 삭제
        postRepository.delete(removePost);
    }

    public Post dtoToEntity(PostDTO postDto) {
        return Post.builder()
                .content(postDto.getContent())
                .fileAttachList(postDto.getFiles())
                .build();
    }

    public PostDTO entityToDto(Post post) {
        return PostDTO.builder()
                .id(post.getId())
                .content(post.getContent())
                .files(post.getFileAttachList())
                .createdDate(post.getCreatedDate().toString())
                .modifiedDate(post.getModifiedDate().toString())
                .build();
    }


}
