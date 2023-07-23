package com.travel.web_oasis.web.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.travel.web_oasis.domain.member.Role;
import com.travel.web_oasis.domain.member.Status;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MemberDTO {

    private Long id;
    private String name;
    private String email;
    private String password;
    private Role role;
    private Status status;
    private String introduction;
    private String picture;
    private String provider;

    private Long followersSize;
    private Long followingSize;
    private Boolean followStatus;

    private Long postSize;


    @Builder
    public MemberDTO(Long id, String name, String email, String password, Role role, Status status, String introduction, String picture, String provider, Long followersSize, Long followingSize, Long postSize) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.status = status;
        this.introduction = introduction;
        this.picture = picture;
        this.provider = provider;
        this.followersSize = followersSize;
        this.followingSize = followingSize;
        this.postSize = postSize;
    }

    @QueryProjection
    public MemberDTO(Long id, String name, String picture, String introduction, Boolean followStatus) {
        this.id = id;
        this.name = name;
        this.picture = picture;
        this.introduction = introduction;
        this.followStatus = followStatus;
    }
}
