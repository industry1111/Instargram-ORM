package com.travel.web_oasis.controller;

import com.travel.web_oasis.config.oauth.dto.PrincipalDetail;
import com.travel.web_oasis.domain.service.follow.FollowService;
import com.travel.web_oasis.web.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class FollowController {

    private final FollowService followService;

    @ResponseBody
    @GetMapping("/addFollow/{toMemberId}")
    public Long following(@PathVariable Long toMemberId, @AuthenticationPrincipal PrincipalDetail principalDetail) {

        Long fromMemberId = principalDetail.getMember().getId();
        return followService.followMember(toMemberId, fromMemberId);
    }

    @ResponseBody
    @GetMapping("/followingList/{memberId}")
    public List<MemberDTO> getFollowingList(@PathVariable Long memberId) {

        return followService.getFollowingList(memberId);
    }

    @ResponseBody
    @GetMapping("/followerList/{memberId}")
    public List<MemberDTO> getFollowerList(@PathVariable Long memberId) {

        return followService.getFollowerList(memberId);
    }
}
