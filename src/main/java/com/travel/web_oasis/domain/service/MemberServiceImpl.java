package com.travel.web_oasis.domain.service;

import com.travel.web_oasis.config.oauth.dto.PrincipalDetail;
import com.travel.web_oasis.domain.member.Member;
import com.travel.web_oasis.domain.repository.MemberRepository;
import com.travel.web_oasis.web.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    Logger log = org.slf4j.LoggerFactory.getLogger(MemberServiceImpl.class);


    @Override
    public Boolean validateDuplicateMember(MemberDTO memberDTO) {
        log.info("validationDuplicateMember() start");
        if (findByEmailAndProvider(memberDTO.getEmail(), memberDTO.getProvider())!=null) {
            return false;
        }
        return true;
    }

    @Override
    public Long saveMember(MemberDTO memberDTO) {
        log.info("saveMember() start");
        if (!validateDuplicateMember(memberDTO)) {
            return -1L;
        }
        Member member = dtoToEntity(memberDTO);

        return memberRepository.save(member).getId();
    }

    @Override
    public MemberDTO updateMember(MemberDTO memberDto, PrincipalDetail principalDetail){
        log.info("updateMember() start");
        Member member = findByIdAndProvider(memberDto.getId(), memberDto.getProvider());
        member.update(memberDto);
        Member updateMember = memberRepository.save(member);
        principalDetail.setMember(updateMember);

        return entityToDto(updateMember);

    }


    public Member findByEmailAndProvider(String email,String provider) {
        return memberRepository.findByEmailAndProvider(email,provider);
    }

    public Member findByIdAndProvider(Long id, String provider) {
        return memberRepository.findByIdAndProvider(id, provider);
    }

    public Member dtoToEntity(MemberDTO memberDTO) {

        return Member.builder()
                .email(memberDTO.getEmail())
                .name(memberDTO.getName())
                .password(passwordEncoder.encode(memberDTO.getPassword()))
                .status(memberDTO.getStatus())
                .role(memberDTO.getRole())
                .is_Auth(false)
                .provider(memberDTO.getProvider())
                .bio(memberDTO.getBio())
                .build();

    }
    public MemberDTO entityToDto(Member member) {
        return MemberDTO.builder()
                .id(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .password(member.getPassword())
                .role(member.getRole())
                .status(member.getStatus())
                .bio(member.getBio())
                .picture(member.getPicture())
                .provider(member.getProvider())
                .build();

    }





}