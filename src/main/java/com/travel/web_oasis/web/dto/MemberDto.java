package com.travel.web_oasis.web.dto;

import com.travel.web_oasis.domain.member.Member;
import com.travel.web_oasis.domain.member.Role;
import com.travel.web_oasis.domain.member.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDto {
    private Long id;
    private String name;
    private String email;
    private String password;
    private String phone;
    private Role role;
    private Status status;


}
