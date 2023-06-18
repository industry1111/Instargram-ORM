package com.travel.web_oasis.web.dto;

import com.travel.web_oasis.domain.member.Role;
import com.travel.web_oasis.domain.member.Status;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberDTO {
    private Long id;
    private String name;
    private String email;
    private String password;
    private String phone;
    private Role role;
    private Status status;

}
