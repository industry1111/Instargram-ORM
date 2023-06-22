package com.travel.web_oasis.web.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDTO {

    private Long id;
    private String content;
    private List<MultipartFile> files;
    private String createdDate;
    private String modifiedDate;
}
