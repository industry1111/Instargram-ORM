package com.travel.web_oasis.domain.service.member;

import com.travel.web_oasis.config.oauth.dto.PrincipalDetail;
import com.travel.web_oasis.domain.member.Member;
import com.travel.web_oasis.domain.member.Role;
import com.travel.web_oasis.domain.member.Status;
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
    private final PasswordEncoder passwordEncoder;
    Logger log = org.slf4j.LoggerFactory.getLogger(MemberServiceImpl.class);

    @Value("${profile.storage.path}")
    private String storagePath;

    @Override
    public Long saveMember(MemberDTO memberDTO) {

        log.info("saveMember() start");

        if (findByEmailAndProvider(memberDTO.getEmail(), "N") != null) {
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
    public MemberDTO login(MemberDTO memberDTO) {

        Member member = findByEmailAndProvider(memberDTO.getEmail(), memberDTO.getProvider());

        if (member != null) {
            return entityToDto(member);
        } else {
            return null;
        }

    }

    @Override
    public MemberDTO updateMember(MemberDTO memberDto, Long memberId, MultipartFile file) {
        log.info("updateMember() start");

        Member member = findById(memberId);

        if (file != null) {
            String picture = member.getPicture();

            if ("profileImg.png".equals(picture)) {
                deleteProfileFromStorage(member.getPicture());
            }

            String profileStoreName = pictureUpload(file);

            memberDto.setPicture(profileStoreName);
        }
        member.update(memberDto);

        Member updateMember = memberRepository.save(member);

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