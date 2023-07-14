package com.travel.web_oasis.domain.service;

import com.travel.web_oasis.config.oauth.dto.PrincipalDetail;
import com.travel.web_oasis.domain.member.Member;
import com.travel.web_oasis.domain.member.Role;
import com.travel.web_oasis.domain.member.Status;
import com.travel.web_oasis.domain.repository.follow.FollowRepository;
import com.travel.web_oasis.domain.repository.member.MemberRepository;
import com.travel.web_oasis.web.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.core.Authentication;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;
    private final FollowRepository followRepository;
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

        MemberDTO saveMemberDTO = MemberDTO.builder()
                .email(memberDTO.getEmail())
                .name(memberDTO.getName())
                .password(encryptPassword(memberDTO.getPassword()))
                .role(Role.USER)
                .status(Status.PUBLIC)
                .provider("N")
                .picture("profileImg.png")
                .build();


        Member member = dtoToEntity(saveMemberDTO);

        return memberRepository.save(member).getId();
    }

    @Override
    public MemberDTO updateMember(MemberDTO memberDto, Long memberId, MultipartFile file) {
        log.info("updateMember() start");

        Member member = findById(memberId);

        if (file != null) {
            deleteProfileFromStorage(member.getPicture());
            String profileStoreName = pictureUpload(file);

            memberDto.setPicture(profileStoreName);
        }
        member.update(memberDto);

        Member updateMember = memberRepository.save(member);

        // 현재 사용자의 인증 정보 업데이트
        updateAuthentication(updateMember);

        return entityToDto(updateMember);

    }

    private static void updateAuthentication(Member member) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {

            // 인증 정보에서 Principal 개체를 가져오기
            PrincipalDetail principalDetail = (PrincipalDetail) authentication.getPrincipal();

            // Principal 개체에서 회원 정보를 수정된 멤버로 업데이트
            principalDetail.setMember(member);

            // 업데이트된 Principal 개체를 저장
            authentication.setAuthenticated(true);
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }
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

    private void deleteProfileFromStorage(String profileStoreName) {

        Path storePath = Paths.get(getFullPath(profileStoreName));
        //파일이 존재할 때만 삭제
        try {
            if (Files.exists(storePath)) {
                Files.delete(storePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public String getFullPath(String profileStoreName) {
        return storagePath + profileStoreName;
    }

    Member findByEmail(String mail) {
        return memberRepository.findByEmail(mail);
    }

    @Override
    public MemberDTO getMemberProfile(Long memberId) {

        return memberRepository.getMemberProfile(memberId);
    }

    @Override
    public List<MemberDTO> getSuggestMembers(List<Long> membersIds, Long myId) {
        if ( membersIds == null) {
            membersIds = new ArrayList<>();
        }
        membersIds.add(myId);
        return memberRepository.getMemberList(membersIds, myId);

    }

}