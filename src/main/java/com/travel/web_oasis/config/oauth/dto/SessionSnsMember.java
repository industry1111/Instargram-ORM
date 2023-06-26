package com.travel.web_oasis.config.oauth.dto;

import com.travel.web_oasis.config.oauth.domain.SnsMember;
import com.travel.web_oasis.domain.member.Role;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionSnsMember implements Serializable {
    private String name;
    private String email;
    private String picture;
    private Role role;

    public SessionSnsMember(SnsMember snsMember) {
        this.name = snsMember.getName();
        this.email = snsMember.getEmail();
        this.picture = snsMember.getPicture();
        this.role = snsMember.getRole();

    }

}
