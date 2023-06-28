package com.travel.web_oasis.domain.service;

import com.travel.web_oasis.domain.files.FileAttach;
import com.travel.web_oasis.domain.posts.Post;
import com.travel.web_oasis.domain.repository.PostRepository;
import com.travel.web_oasis.web.dto.PostDTO;
import com.travel.web_oasis.web.dto.ResponseDTO;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Transactional
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
    public ResponseDTO<PostDTO> getPost(Long id) {

        String message = "";
        PostDTO postDTO = new PostDTO();
        Post post =  postRepository.findById(id).orElse(null);

        if (post == null) {
            message = "해당 게시글이 존재하지 않습니다.";
        } else {
            postDTO = entityToDto(post);
        }
        return new ResponseDTO<PostDTO>(200, message, postDTO);
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
