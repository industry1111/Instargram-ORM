package com.travel.web_oasis.web.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@Data
public class FollowDTO {

    private Long fromMemberId;
    private Long toMemberId;

    @Builder
    public FollowDTO (Long toMemberId, Long fromMemberId) {

        this.toMemberId = toMemberId;
        this.fromMemberId = fromMemberId;
    }

}
