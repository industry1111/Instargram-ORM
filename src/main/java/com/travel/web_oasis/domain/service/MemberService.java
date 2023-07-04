package com.travel.web_oasis.domain.service;

import com.travel.web_oasis.config.oauth.dto.PrincipalDetail;
import com.travel.web_oasis.web.dto.MemberDTO;


public interface  MemberService  {
    public Long saveMember(MemberDTO memberDTO);
    public MemberDTO updateMember(MemberDTO memberDto, PrincipalDetail principalDetail);
    public Boolean validateDuplicateMember(MemberDTO memberDTO);



}
