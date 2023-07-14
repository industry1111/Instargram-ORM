package com.travel.web_oasis.domain.repository.member;

import com.travel.web_oasis.web.dto.MemberDTO;

import java.util.List;

public interface CustomMemberRepository {

    List<MemberDTO> getMemberList(List<Long> membersIds, Long myId);

    MemberDTO getMemberProfile(Long memberId);
}
