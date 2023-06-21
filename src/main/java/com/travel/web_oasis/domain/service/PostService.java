package com.travel.web_oasis.domain.service;


import com.travel.web_oasis.domain.posts.Post;
import com.travel.web_oasis.web.dto.PostDTO;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public abstract interface PostService {

    Post createPost(PostDTO postDTO, MultipartFile[] files);
    Post findById(Long id);


}

