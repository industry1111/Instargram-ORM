package com.travel.web_oasis.domain.posts;

import com.travel.web_oasis.domain.BaseEntity;
import com.travel.web_oasis.domain.files.FilesAttach;
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
    private List<FilesAttach> filesAttachList = new ArrayList<>();

    public void addFile(FilesAttach filesAttach) {
        this.filesAttachList.add(filesAttach);
        filesAttach.setPost(this);
    }

    @Builder
    public Post(String content,List<FilesAttach> filesAttachList) {
        this.content = content;

        for (FilesAttach file : filesAttachList) {
            this.addFile(file);
        }
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", filesAttachList=" + filesAttachList +
                '}';
    }


}