package com.travel.web_oasis.domain.service;

import com.travel.web_oasis.domain.member.Member;
import com.travel.web_oasis.domain.repository.MemberRepository;
import com.travel.web_oasis.web.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;
   public PasswordEncoder passwordEncoder;
    @Override
    public Long saveMember(MemberDTO memberDTO) {
        Member member = dtoToEntity(memberDTO);
        validateDuplicateMember(member);
        return memberRepository.save(member).getId();
    }

    @Override
    public Long updateMember(MemberDTO memberDto) {
        Member member = memberRepository.findById(memberDto.getId())
                .orElseThrow(IllegalStateException::new);
        member.update(memberDto);

        return memberRepository.save(member).getId();

    }
    @Override
    public void validateDuplicateMember(Member member) {
        Member findMember = memberRepository.findByEmail(member.getEmail());
        if (findMember != null) {
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }


    public Member dtoToEntity(MemberDTO memberDTO) {

        return Member.builder()
                .email(memberDTO.getEmail())
                .name(memberDTO.getName())
                .password(passwordEncoder.encode(memberDTO.getPassword()))
                .phone(memberDTO.getPhone())
                .status(memberDTO.getStatus())
                .role(memberDTO.getRole())
                .build();

    }





}