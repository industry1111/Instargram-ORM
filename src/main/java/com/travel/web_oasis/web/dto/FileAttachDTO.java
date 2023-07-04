package com.travel.web_oasis.web.dto;

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

    private String fileUrl;

    private String fileType;

    private Long fileSize;
}
