package com.travel.web_oasis.domain.service;

import com.travel.web_oasis.domain.member.Member;
import com.travel.web_oasis.domain.repository.MemberRepository;
import com.travel.web_oasis.web.dto.MemberDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public Long saveMember(MemberDto memberDto) {

        Member member = DtoToEntity(memberDto);

        memberRepository.save(member);
        return memberRepository.save(member).getId();
    }

    public Member findById(Long id) {

        return memberRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("해당 멤버가 없습니다. id = " + id));

    }

    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
    }

    public void updateMember(Long id, MemberDto memberDto) {

        Member member = memberRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        member.update(memberDto);

        memberRepository.save(member);

    }

    public Member DtoToEntity(MemberDto memberDto) {
        return Member.builder()
                .name(memberDto.getName())
                .email(memberDto.getEmail())
                .password(memberDto.getPassword())
                .phone(memberDto.getPhone())
                .role(memberDto.getRole())
                .status(memberDto.getStatus())
                .build();
    }

    public MemberDto EntityToDto(Member member) {
        MemberDto memberDto = new MemberDto();
        memberDto.setId(member.getId());
        memberDto.setName(member.getName());
        memberDto.setEmail(member.getEmail());
        memberDto.setPassword(member.getPassword());
        memberDto.setPhone(member.getPhone());
        memberDto.setRole(member.getRole());
        memberDto.setStatus(member.getStatus());

        return memberDto;
    }


}
