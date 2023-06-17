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
public class Files extends BaseEntity {

    @Id @GeneratedValue
    private Long id;

    @Column(length = 20)
    private String fileName;

    private String filePath;

    @Builder
    public Files(String fileName, String filePath) {
        this.fileName = fileName;
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return "Files{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", filePath='" + filePath + '\'' +
                '}';
    }
}
