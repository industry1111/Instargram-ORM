package com.travel.web_oasis.controller;

import com.travel.web_oasis.domain.posts.Post;
import com.travel.web_oasis.domain.service.PostService;
import com.travel.web_oasis.domain.service.PostServiceImpl;
import com.travel.web_oasis.web.dto.PostDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("/post")
@Controller
public class PostController {

    @Autowired
    private PostServiceImpl postService;

    /*
    * @PostDTO : 게시글 내용과 파일 정보를 담은 DTO
    * @RequestPart("files") List<MultipartFile> : 파일 정보를 담은 List
    *
    * */
    @PostMapping("/create")
    public String createPost(@ModelAttribute PostDTO postDTO, @RequestPart("file") MultipartFile[] files) {
        Post savedPost = postService.createPost(postDTO, files);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String getPost(@PathVariable Long id, Model model) {

        Post post = postService.findById(id);
        model.addAttribute("post", post);

        return "/post/postDetail";
    }


}
