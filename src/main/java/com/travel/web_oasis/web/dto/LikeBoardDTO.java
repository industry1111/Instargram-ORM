package com.travel.web_oasis.web.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LikeBoardDTO {

    private Long memberId;

    private Long postId;

}
