package com.travel.web_oasis.web.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.travel.web_oasis.domain.service.Commnet.CommentDTO;
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
    private MemberDTO memberDTO;

    //like
    private boolean like;

    //comment
    private List<CommentDTO> commentDTOS;

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    @QueryProjection
    public PostDTO(Long id, String content, MemberDTO memberDTO, List<CommentDTO> commentDTOS,  LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.content = content;
        this.memberDTO = memberDTO;
        this.commentDTOS = commentDTOS;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}
