package com.travel.web_oasis.domain.service;

import com.travel.web_oasis.domain.files.FileAttach;
import com.travel.web_oasis.domain.posts.Post;
import com.travel.web_oasis.domain.repository.PostRepository;
import com.travel.web_oasis.web.dto.PostDTO;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;

    private final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);

    @Override
    @Transactional
    public Long createPost(PostDTO postDTO, List<MultipartFile> files) {

        logger.info("createPost() called   PostDTO : {}", postDTO);

        Post post = dtoToEntity(postDTO);

        Long id = postRepository.save(post).getId();

        return id;
    }

    @Override
    public PostDTO findPost(Long id) {

        Post post =  postRepository.findById(id).orElseThrow(null);

        if (post == null) {
            return null;
        }

        return entityToDto(post);

    }

    @Override
    public void deletePost(Long id) {
        //삭제할 포스트
//        PostDTO removePostDTO = getPost(id);
        //삭제할 포스트의 첨부파일들
//        List<FileAttach> files = removePostDTO.getFiles();
//        System.out.println("files = " + files);
//        for (FileAttach file : files) {
//
//            log.info("deletePost() called   file : {}", file.toString());
//
//        }
//        //저장소에서 첨부파일들 삭제
//        fileAttachService.deleteFiles(files);
//        //포스트 삭제
//        postRepository.deleteById(id);
    }

    @Override
    public List<PostDTO> findAllPost() {

        List<Post> findList = postRepository.findAll();

        List<PostDTO> resultList = new ArrayList<>();

        for (Post post : findList) {
            resultList.add(entityToDto(post));
        }

        return resultList;
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
