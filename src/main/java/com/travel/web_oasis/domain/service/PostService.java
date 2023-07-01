package com.travel.web_oasis.domain.service;


import com.travel.web_oasis.web.dto.PostDTO;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public abstract interface PostService {

    Long createPost(PostDTO postDTO, List<MultipartFile> files);
    PostDTO findPost(Long id);

    List<PostDTO> findAllPost();
    void deletePost(Long id);
}

