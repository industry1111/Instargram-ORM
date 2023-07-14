package com.travel.web_oasis.domain.service;

import com.travel.web_oasis.domain.member.Member;
import com.travel.web_oasis.domain.member.Role;
import com.travel.web_oasis.domain.member.Status;
import com.travel.web_oasis.domain.repository.member.MemberRepository;
import com.travel.web_oasis.web.dto.MemberDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberServiceImpl memberService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    MemberDTO newMember() {
        return MemberDTO.builder()
                .name("테스트")
                .email("test@mail.com")
                .password("1234")
                .role(Role.USER)
                .status(Status.PUBLIC)
                .build();
    }

    MemberDTO update(Long id) {
        return MemberDTO.builder()
                .id(id)
                .name("테스트2")
                .password("123")
                .status(Status.PRIVATE)
                .build();
    }

    @Test
    void saveMember() {
        memberService.saveMember(newMember());
    }

    @Test
    void deleteMember() {

        Long id = memberService.saveMember(newMember());

        Member member = memberService.findById(id);

        memberRepository.deleteById(member.getId());

    }

    @Test
    void updateMember() {
        Long id = memberService.saveMember(newMember());
        MemberDTO memberDTO = update(id);
//        memberService.updateMember(memberDTO, null);

        Member member = memberService.findById(id);

        System.out.println(member.getId());

        Assertions.assertEquals("테스트2", member.getName());
    }

    @Test
    void updateProfile() throws IOException {
        MemberDTO memberDTO = newMember();

        Long memberId = memberService.saveMember(memberDTO);

        String imagePath = "/Users/gohyeong-gyu/Downloads/logo.png";
        byte[] imageBytes = Files.readAllBytes(Paths.get(imagePath));

        MultipartFile profileImg = new MockMultipartFile(
                "file",
                "image.jpg",
                "image/jpeg",
                imageBytes
        );
        memberService.updateMember(memberDTO,memberId,profileImg);
    }

    @Test
    void getMemberList() {
        //given
        ArrayList<Long> membersIds = new ArrayList<>();
//        membersIds.add(1L);
//        membersIds.add(2L);

        //when
        List<MemberDTO> memberList = memberService.getSuggestMembers(membersIds, 52L);

        //then
        for (MemberDTO memberDTO : memberList) {
            System.out.println("memberDTO.getId() = " + memberDTO.getId());
            assertThat(memberDTO.getId()).isNotIn(membersIds);
        }

    }

    @Test
    @DisplayName("프로필 정보 가져오기")
    void getMemberProfile() {
        //given
        Long memberId = 1L;

        //when
        MemberDTO memberDTO = memberService.getMemberProfile(memberId);

        //then

    }
}