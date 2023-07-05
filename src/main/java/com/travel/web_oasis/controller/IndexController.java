package com.travel.web_oasis.controller;

import com.travel.web_oasis.config.oauth.dto.PrincipalDetail;
import com.travel.web_oasis.domain.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostService postService;

    @GetMapping("/")
//    public String index(Model model, Authentication authentication) {
//        PrincipalDetail principal = (PrincipalDetail) authentication.getPrincipal();
//        System.out.println("model = " + model + ", authentication = " + authentication);
//        System.out.println("principal = " + principal);
//        if (principal.getMember().getIs_Auth()) {
//            model.addAttribute("member", principal.getMember());
//        } else {
//            model.addAttribute("member", principal.getMember());
//        }
//        return "/layouts/layout1";
//    }
    public String index(@AuthenticationPrincipal PrincipalDetail principal, Model model) {
        if (principal != null) {
            model.addAttribute("member", principal.getMember());
            System.out.println("principal = " + principal.getMember().toString());
        }
//        PageRequestDTO pageRequestDTO = new PageRequestDTO();
//        model.addAttribute("pageRequest", pageRequestDTO);

//        PageResultDTO<PostDTO, Post> list = postService.getList(pageRequestDTO);
//
//        for(PostDTO postDTO : list.getDtoList()) {
//            System.out.println("postDTO = " + postDTO.toString());
//        }

        return "/main";
    }
}