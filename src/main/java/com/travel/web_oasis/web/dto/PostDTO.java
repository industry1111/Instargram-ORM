package com.travel.web_oasis.web.dto;

import com.travel.web_oasis.domain.files.FileAttach;
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
    private List<FileAttach> files;
    private String createdDate;
    private String modifiedDate;
}
