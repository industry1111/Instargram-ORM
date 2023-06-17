package com.travel.web_oasis.domain.posts;

import com.travel.web_oasis.domain.BaseEntity;
import com.travel.web_oasis.domain.files.Files;
import com.travel.web_oasis.domain.member.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Post extends BaseEntity {

    @Id @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 3000)
    private String content;

    @OneToMany
    @JoinColumn(name = "post_id")
    private List<Files> files = new ArrayList<>();

    public void addFile(Files file) {
        this.files.add(file);
    }

    @Builder
    public Post(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", files=" + files +
                '}';
    }

}