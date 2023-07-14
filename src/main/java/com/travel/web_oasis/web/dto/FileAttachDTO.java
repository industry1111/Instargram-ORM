package com.travel.web_oasis.web.dto;

import com.querydsl.core.annotations.QueryProjection;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FileAttachDTO {

    private Long id;

    private String fileName;

    private String fileStoreName;

    private String fileType;

    private Long fileSize;

    @QueryProjection
    public FileAttachDTO(String fileName, String fileStoreName) {
        this.fileName = fileName;
        this.fileStoreName = fileStoreName;
    }
}
