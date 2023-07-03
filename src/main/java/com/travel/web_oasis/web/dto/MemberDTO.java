package com.travel.web_oasis.web.dto;

import com.travel.web_oasis.domain.member.Member;
import com.travel.web_oasis.domain.member.Role;
import com.travel.web_oasis.domain.member.Status;
import lombok.*;

@Getter
@Builder
@Setter
@ToString
@AllArgsConstructor
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

    public MemberDTO entityToDto(Member member) {
        return MemberDTO.builder()
                .id(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .password(member.getPassword())
                .role(member.getRole())
                .status(member.getStatus())
                .introduction(member.getIntroduction())
                .picture(member.getPicture())
                .build();
    }
}
