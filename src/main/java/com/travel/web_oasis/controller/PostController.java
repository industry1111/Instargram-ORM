package com.travel.web_oasis.controller;

import com.travel.web_oasis.config.oauth.dto.PrincipalDetail;
import com.travel.web_oasis.domain.entity.LikeBoard;
import com.travel.web_oasis.domain.member.Member;
import com.travel.web_oasis.domain.entity.Post;
import com.travel.web_oasis.domain.service.FileAttachService;
import com.travel.web_oasis.domain.service.LikeBoardService;
import com.travel.web_oasis.domain.service.MemberService;
import com.travel.web_oasis.domain.service.PostService;
import com.travel.web_oasis.web.dto.*;
import groovy.util.logging.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @Autowired
    MemberService memberService;

    @Autowired
    LikeBoardService likeBoardService;

    private final static Logger logger = LoggerFactory.getLogger(PostController.class);


    /*
     * @Param
     *   postDTO : 게시글 내용과 파일 정보를 담은 DTO
     *   files : 파일 정보를 담은 MultipartFile 배열
     *   principalDetail :
     * @Description : 게시글 생성
     *
     * @Return : 게시글 생성 후 메인 페이지로 이동
     * */
    @ResponseBody
    @PostMapping("/create")
    public Long createPost(@ModelAttribute PostDTO postDTO, @RequestParam("file") List<MultipartFile> files, @AuthenticationPrincipal PrincipalDetail principalDetail) {

        logger.info("createPost Method  Start \n PostDTO : {}, files : {}", postDTO, files);

        Member member = principalDetail.getMember();

        List<FileAttachDTO> fileAttachDTOList = fileAttachService.upload(files);

        postDTO.setFiles(fileAttachDTOList);

        return postService.createPost(postDTO, member);
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
    public PostDTO findPost(@PathVariable Long id) {

        logger.info("findPost Controller Start \n id={}", id);

        PostDTO postDTO = postService.findPost(id);

        return postDTO;
    }

    /*
     * @Param
     *  id : 게시글 id
     * @Description : 게시글 삭제
     *
     * @Return : 메인 페이지로 이동
     * */
    @GetMapping("/delete/{id}")
    public String deletePost(@PathVariable Long id) {
        logger.info("deletePost start");
        postService.deletePost(id);
        return "redirect:/";
    }


    @ResponseBody
    @GetMapping("/download/{fileStoreName}")
    public ResponseEntity<Resource> downloadImage(@PathVariable String fileStoreName) throws MalformedURLException {
        logger.info("downloadImage start \n fileStoreName={}", fileStoreName);
        Resource resource = new UrlResource("file:" + fileAttachService.getFullPath(fileStoreName));

        if (resource.exists()) {

            String contentType = fileAttachService.getFileType(fileStoreName);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @ResponseBody
    @GetMapping("/list/{id}")
    public PageResultDTO<PostDTO, Post> list(PageRequestDTO pageRequestDTO, @PathVariable Long id) {
        logger.info("postController list start \n pageRequestDTO={}, id={}", pageRequestDTO, id);

        return postService.getPostList(pageRequestDTO, id);

    }

    @ResponseBody
    @GetMapping("/member/postList/{memberId}")
    public PageResultDTO<PostDTO, Post> findMemberPostList(PageRequestDTO pageRequestDTO, @PathVariable Long memberId) {
        logger.info("postController list start \n pageRequestDTO={}, id={}", pageRequestDTO, memberId);

        return postService.getMemberPostList(pageRequestDTO, memberId);
    }

    @ResponseBody
    @GetMapping("/api/addLike")
    public Long addLikeBoard(@ModelAttribute Long postId, @AuthenticationPrincipal PrincipalDetail principalDetail) {


        return likeBoardService.addLikeBoard(postId, principalDetail.getMember().getId());

    }

    @ResponseBody
    @GetMapping("/api/duplicate")
    public List<LikeBoard> duplicateLikeBoard(@AuthenticationPrincipal PrincipalDetail principalDetail) {
        return likeBoardService.findByMember(principalDetail.getMember().getId());
    }
}
