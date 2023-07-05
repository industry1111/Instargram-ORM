package com.travel.web_oasis.domain.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "id")
@NoArgsConstructor
@Entity
public class FileAttach extends BaseEntity {

    @Id @GeneratedValue
    private Long id;

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
    public FileAttach(String fileName, String fileStoreName, String fileUrl,String fileType,Long fileSize ) {
        this.fileName = fileName;
        this.fileStoreName = fileStoreName;
        this.fileUrl = fileUrl;
        this.fileType = fileType;
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
