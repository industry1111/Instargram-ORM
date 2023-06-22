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
    * @Param
    *   postDTO : 게시글 내용과 파일 정보를 담은 DTO
    *   files : 파일 정보를 담은 MultipartFile 배열
    * @Description : 게시글 생성
    *
    * @Return : 게시글 생성 후 메인 페이지로 이동
    * */
    @PostMapping("/create")
    public String createPost(@ModelAttribute PostDTO postDTO, @RequestParam("files") List<MultipartFile> files) {
        postDTO.setFiles(files);
        Post savedPost = postService.createPost(postDTO);
        return "redirect:/";
    }


    /*
     * @Param
     *  id : 게시글 id
     * @Description : 게시글 상세보기
     *
     * @Return : model에 게시글 정보를 담아 게시글 상세보기 페이지로 이동
     * */
    @GetMapping("/{id}")
    public String getPost(@PathVariable Long id, Model model) {

        Post post = postService.findById(id);
        model.addAttribute("post", post);

        return "/post/postDetail";
    }

    /*
     * @Param
     *  id : 게시글 id
     * @Description : 게시글 삭제
     *
     * @Return : 메인 페이지로 이동
     * */
    @DeleteMapping("/{id}")
    public String deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return "redirect:/";
    }



}
