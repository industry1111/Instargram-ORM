package com.travel.web_oasis.domain.posts;

import com.travel.web_oasis.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Post extends BaseEntity {

    @Id @GeneratedValue
    Long id;

    String content;

    @Builder
    public Post(String content) {
        this.content = content;
    }
}