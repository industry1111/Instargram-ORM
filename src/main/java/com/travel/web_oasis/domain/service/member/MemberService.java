package com.travel.web_oasis.domain.service.member;

import com.travel.web_oasis.domain.member.Member;
import com.travel.web_oasis.web.dto.MemberDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface MemberService {

    Long saveMember(MemberDTO memberDTO);

    MemberDTO login(MemberDTO memberDTO);

    MemberDTO updateMember(MemberDTO memberDto, Long memberId, MultipartFile file);

    Member findById(Long id);

    String getFullPath(String picture);

    MemberDTO getMemberProfile(Long memberId);

    List<MemberDTO> getSuggestMembers(List<Long> membersIds, Long myId);

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
                .role(member.getRole())
                .status(member.getStatus())
                .introduction(member.getIntroduction())
                .picture(member.getPicture())
                .provider(member.getProvider())
                .build();

    }

}
