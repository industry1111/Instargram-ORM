package com.travel.web_oasis.domain.posts;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.travel.web_oasis.domain.BaseEntity;
import com.travel.web_oasis.domain.files.FileAttach;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static com.fasterxml.jackson.annotation.ObjectIdGenerators.*;

@Getter
@JsonIdentityInfo(generator = IntSequenceGenerator.class, property = "id")
@NoArgsConstructor
@Entity
public class Post extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    @Column(nullable = false, length = 3000)
    private String content;

    @OneToMany(mappedBy = "post", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<FileAttach> fileAttachList = new ArrayList<>();

    public void addFile(FileAttach fileAttach) {
        this.fileAttachList.add(fileAttach);
        fileAttach.setPost(this);
    }

    @Builder
    public Post(Long id, String content,List<FileAttach> fileAttachList) {
        this.id = id;
        this.content = content;
        for (FileAttach file : fileAttachList) {
            this.addFile(file);
        }
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", filesAttachList=" + fileAttachList +
                '}';
    }


}