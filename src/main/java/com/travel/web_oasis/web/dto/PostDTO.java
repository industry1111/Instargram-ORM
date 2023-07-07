package com.travel.web_oasis.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
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

    //like
    private boolean like;




    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}
