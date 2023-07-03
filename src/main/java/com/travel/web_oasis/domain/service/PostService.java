package com.travel.web_oasis.domain.service;


import com.travel.web_oasis.domain.member.Member;
import com.travel.web_oasis.domain.posts.Post;
import com.travel.web_oasis.web.dto.PostDTO;

import java.util.List;

public abstract interface PostService {

    Long createPost(PostDTO postDTO, Member member);
    PostDTO findPost(Long id);

    List<PostDTO> findAllPost();
    void deletePost(Long id);

    default Post dtoToEntity(PostDTO postDto) {
        return Post.builder()
                .content(postDto.getContent())
                .fileAttachList(FileAttachServiceImpl.dtosToEntities(postDto.getFiles()))
                .build();
    }

    default PostDTO entityToDto(Post post) {
        return PostDTO.builder()
                .id(post.getId())
                .content(post.getContent())
                .memberId(post.getMember().getId())
                .files(FileAttachServiceImpl.entitiesToDtos(post.getFileAttachList()))
                .createdDate(post.getCreatedDate().toString())
                .modifiedDate(post.getModifiedDate().toString())
                .build();
    }
}

