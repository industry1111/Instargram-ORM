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

    private int followersSize;
    private int followingSize;


    @Builder
    public MemberDTO(Long id, String name, String email, String password, Role role, Status status, String introduction, String picture, String provider, int followersSize, int followingSize) {
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
    }

    @QueryProjection
    public MemberDTO(Long id, String name, String picture, String introduction) {
        this.id = id;
        this.name = name;
        this.picture = picture;
        this.introduction = introduction;
    }
}
