package com.travel.web_oasis.domain.service;

import com.travel.web_oasis.config.oauth.dto.PrincipalDetail;
import com.travel.web_oasis.domain.member.Member;
import com.travel.web_oasis.domain.repository.MemberRepository;
import com.travel.web_oasis.web.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    Logger log = org.slf4j.LoggerFactory.getLogger(MemberServiceImpl.class);

    @Value("${profile.storage.path}")
    private String storagePath;

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

        String encryptedPassword = encryptPassword(memberDTO.getPassword());
        memberDTO.setPassword(encryptedPassword);

        Member member = dtoToEntity(memberDTO);

        return memberRepository.save(member).getId();
    }

    @Override
    public MemberDTO updateMember(MemberDTO memberDto, PrincipalDetail principalDetail, MultipartFile file) {
        log.info("updateMember() start");

        Member member = principalDetail.getMember();

        if (file != null) {
            String profileStoreName = pictureUpload(file);

            memberDto.setPicture(profileStoreName);
        }
        member.update(memberDto);

        Member updateMember = memberRepository.save(member);

        principalDetail.setMember(updateMember);

        return entityToDto(updateMember);

    }

    public Member findByEmailAndProvider(String email,String provider) {
        return memberRepository.findByEmailAndProvider(email,provider);
    }

    @Override
    public Member findById(Long id) {
        Optional<Member> result = memberRepository.findById(id);
        return result.get();
    }

    public String pictureUpload(MultipartFile file) {

        String fileName = file.getOriginalFilename();

        int pos = fileName.lastIndexOf(".");

        String ext = fileName.substring(pos+1);

        String uuid = UUID.randomUUID().toString();

        String storeFileName = uuid + "." + ext;

        try{
            file.transferTo(new File(storagePath + storeFileName));
        }catch (Exception e){
            e.printStackTrace();
        }
        return storeFileName;
    }

    private String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public String getFullPath(Long id) {
        Member member = findById(id);
        return storagePath + member.getPicture();
    }
}