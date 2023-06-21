package com.travel.web_oasis.web.dto;

import com.travel.web_oasis.domain.files.FilesAttach;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDTO {

    private Long id;
    private String content;
    private List<FilesAttach> files;
    private String createdDate;
    private String modifiedDate;
}
