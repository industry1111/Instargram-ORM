package com.travel.web_oasis.web.dto;

import com.travel.web_oasis.domain.posts.Post;
import lombok.Getter;

@Getter
public class PostResponseDto {

    Long id;
    String content;
    String createdBy;
    String createdDate;
    String modifiedBy;
    String modifiedDate;

    public PostResponseDto(Post entity) {
        this.id = entity.getId();
        this.content = entity.getContent();
        this.createdBy = entity.getCreatedBy();
        this.createdDate = entity.getCreatedDate().toString();
        this.modifiedBy = entity.getModifiedBy();
        this.modifiedDate = entity.getModifiedDate().toString();
    }
}
