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
    private final PasswordEncoder passwordEncoder;

    @Override
    public Long saveMember(MemberDTO memberDTO) {
        if (!validateDuplicateMember(memberDTO)) {
            return -1L;
        }
        Member member = dtoToEntity(memberDTO);

        return memberRepository.save(member).getId();
    }

    @Override
    public Long updateMember(MemberDTO memberDto) {
        Member member = memberRepository.findById(memberDto.getId())
                .orElseThrow(IllegalStateException::new);
        System.out.println("member = " + member);
        member.update(memberDto);

        return memberRepository.save(member).getId();

    }
    @Override
    public Boolean validateDuplicateMember(MemberDTO memberDTO) {
        if (memberRepository.findByEmail(memberDTO.getEmail())!=null) {
            return false;
        }
        return true;
    }


    public Member dtoToEntity(MemberDTO memberDTO) {

        return Member.builder()
                .email(memberDTO.getEmail())
                .name(memberDTO.getName())
                .password(passwordEncoder.encode(memberDTO.getPassword()))
                .status(memberDTO.getStatus())
                .role(memberDTO.getRole())
                .is_Auth(false)
                .picture("")
                .provider("local")
                .build();

    }





}