package com.travel.web_oasis.controller;

import com.travel.web_oasis.domain.files.FileAttach;
import com.travel.web_oasis.domain.service.FileAttachService;
import com.travel.web_oasis.domain.service.PostService;
import com.travel.web_oasis.web.dto.PostDTO;
import groovy.util.logging.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.util.List;

@Slf4j
@RequestMapping("/post")
@Controller
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    FileAttachService fileAttachService;

    private final static Logger logger = LoggerFactory.getLogger(PostController.class);
    /*
    * @Param
    *   postDTO : 게시글 내용과 파일 정보를 담은 DTO
    *   files : 파일 정보를 담은 MultipartFile 배열
    * @Description : 게시글 생성
    *
    * @Return : 게시글 생성 후 메인 페이지로 이동
    * */
    @ResponseBody
    @PostMapping("/create")
    public Long createPost(@ModelAttribute PostDTO postDTO, @RequestParam("file") List<MultipartFile> files) {

        List<FileAttach> fileAttachList = fileAttachService.upload(files);

        postDTO.setFiles(fileAttachList);

         return postService.createPost(postDTO, files);
    }

    /*
     * @Param
     *  id : 게시글 id
     * @Description : 게시글 상세보기
     *
     * @Return : Post 정보를 JSON 형태로 반환

     */
    @ResponseBody
    @GetMapping("/{id}")
    public PostDTO getPost(@PathVariable Long id) {

        PostDTO postDTO = postService.getPost(id);

        return postDTO;
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


    @ResponseBody
    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> downloadImage(@PathVariable String fileName) throws MalformedURLException {
        Resource resource = new UrlResource("file:" + fileAttachService.getFullPath(fileName));

        if (resource.exists()) {
            String contentType = fileAttachService.getFileType(fileName);
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
