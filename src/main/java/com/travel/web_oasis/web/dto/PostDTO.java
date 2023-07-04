package com.travel.web_oasis.web.dto;

import com.travel.web_oasis.domain.files.FileAttach;
import com.travel.web_oasis.domain.member.Member;
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
    private List<FileAttachDTO> files;

    //FileAttach
    private String[] fileNames;
    private String[] fileStoreNames;


    //Member 정보
    private Long memberId;
    private String name;
    private String email;
    private String provider;
    private String picture;


    private String createdDate;
    private String modifiedDate;
}
