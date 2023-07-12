package com.travel.web_oasis.domain.service.Commnet;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CommentDTO {
    private String content;
    private Long postId;

    //작성자 정보
    private Long memberId;
    private String memberProfileImg;
    private String memberName;


    @QueryProjection
    @Builder
    public CommentDTO(String content, Long memberId, String memberProfileImg, String memberName) {
        this.content = content;
        this.memberId = memberId;
        this.memberProfileImg = memberProfileImg;
        this.memberName = memberName;
    }

}
