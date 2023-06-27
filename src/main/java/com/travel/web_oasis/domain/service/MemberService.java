package com.travel.web_oasis.domain.service;

import com.travel.web_oasis.domain.member.Member;
import com.travel.web_oasis.web.dto.MemberDTO;


public interface  MemberService  {
    public Long saveMember(MemberDTO memberDTO);
    public Long updateMember(MemberDTO memberDto);
    public void validateDuplicateMember(Member member);



}
