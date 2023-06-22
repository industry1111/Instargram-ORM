package com.travel.web_oasis.domain.posts;

import com.travel.web_oasis.domain.BaseEntity;
import com.travel.web_oasis.domain.files.FileAttach;
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
    @Column(name = "post_id")
    private Long id;

    @Column(nullable = false, length = 3000)
    private String content;

    @OneToMany(mappedBy = "post", orphanRemoval = true)
    private List<FileAttach> fileAttachList = new ArrayList<>();

    public void addFile(FileAttach fileAttach) {
        this.fileAttachList.add(fileAttach);
        fileAttach.setPost(this);
    }

    @Builder
    public Post(String content,List<FileAttach> fileAttachList) {
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