package com.travel.web_oasis.web.dto;

import com.travel.web_oasis.domain.files.Files;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDTO {

    private Long id;
    private String content;
    private List<Files> files;
    private String createdDate;
    private String modifiedDate;
}
