package com.travel.web_oasis.domain.service;

import com.travel.web_oasis.config.oauth.dto.PrincipalDetail;
import com.travel.web_oasis.domain.member.Member;
import com.travel.web_oasis.web.dto.MemberDTO;
import org.springframework.web.multipart.MultipartFile;


public interface MemberService {
    Long saveMember(MemberDTO memberDTO);
    MemberDTO updateMember(MemberDTO memberDto, PrincipalDetail principalDetail, MultipartFile file);
    Boolean validateDuplicateMember(MemberDTO memberDTO);

    Member findById(Long id);

    String getFullPath(Long id);


    default Member dtoToEntity(MemberDTO memberDTO) {

        return Member.builder()
                .email(memberDTO.getEmail())
                .name(memberDTO.getName())
                .password(memberDTO.getPassword())
                .status(memberDTO.getStatus())
                .role(memberDTO.getRole())
                .provider(memberDTO.getProvider())
                .introduction(memberDTO.getIntroduction())
                .picture(memberDTO.getPicture())
                .build();

    }
    default MemberDTO entityToDto(Member member) {
        return MemberDTO.builder()
                .id(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .password(member.getPassword())
                .role(member.getRole())
                .status(member.getStatus())
                .introduction(member.getIntroduction())
                .picture(member.getPicture())
                .provider(member.getProvider())
                .build();

    }


}
