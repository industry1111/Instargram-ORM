package com.travel.web_oasis.domain.files;

import com.travel.web_oasis.domain.BaseEntity;
import com.travel.web_oasis.domain.posts.Post;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class FilesAttach extends BaseEntity {

    @Id @GeneratedValue
    private Long id;

    private String fileOriName;

    @Column(length = 20)
    private String fileName;

    private String fileUrl;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;


    public void setPost(Post post) {
        this.post = post;
    }

    @Builder
    public FilesAttach(String fileOriName, String fileName, String fileUrl) {
        this.fileOriName = fileOriName;
        this.fileName = fileName;
        this.fileUrl = fileUrl;
    }

}
