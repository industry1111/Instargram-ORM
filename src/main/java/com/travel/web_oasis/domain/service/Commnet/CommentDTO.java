package com.travel.web_oasis.domain.service.Commnet;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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


    @Builder
    public CommentDTO(String content, Long postId, Long memberId, String memberProfileImg, String memberName) {
        this.content = content;
        this.postId = postId;
        this.memberId = memberId;
        this.memberProfileImg = memberProfileImg;
        this.memberName = memberName;
    }
}
