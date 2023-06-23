package com.travel.web_oasis.domain.files;

import com.travel.web_oasis.domain.BaseEntity;
import com.travel.web_oasis.domain.posts.Post;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@Entity
public class FileAttach extends BaseEntity {

    @Id @GeneratedValue
    private Long id;

    @Column(length = 20)
    private String fileName;

    private String fileStoreName;

    private String fileUrl;

    private String fileType;

    private Long fileSize;


    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public void setPost(Post post) {
        this.post = post;
    }

    @Builder
    public FileAttach(String fileName, String fileStoreName, String fileUrl,String FileType,Long fileSize ) {
        this.fileName = fileName;
        this.fileStoreName = fileStoreName;
        this.fileUrl = fileUrl;
        this.fileType = FileType;
        this.fileSize = fileSize;
    }

    @Override
    public String toString() {
        return "FileAttach{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", fileStoreName='" + fileStoreName + '\'' +
                ", fileUrl='" + fileUrl + '\'' +
                ", fileType='" + fileType + '\'' +
                ", fileSize=" + fileSize +
                '}';
    }
}
