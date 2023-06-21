package com.travel.web_oasis.controller;

import com.travel.web_oasis.domain.posts.Post;
import com.travel.web_oasis.domain.service.PostService;
import com.travel.web_oasis.web.dto.PostDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("/post")
@RequiredArgsConstructor
@Controller
public class PostController {

    private final PostService postService;

    /*
    * @PostDTO : 게시글 내용과 파일 정보를 담은 DTO
    * @RequestParam("files") List<MultipartFile> files : 파일 정보를 담은 List
    *
    * */
    @PostMapping("/create")
    public String createPost(@ModelAttribute PostDTO postDTO,@RequestParam("files") List<MultipartFile> files) {

        Post savedPost = postService.createPost(postDTO, files);

        return "redirect:/";


    }


}
