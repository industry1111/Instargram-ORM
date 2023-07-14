package com.travel.web_oasis.controller;

import com.travel.web_oasis.domain.entity.Comment;
import com.travel.web_oasis.domain.service.Commnet.CommentDTO;
import com.travel.web_oasis.domain.service.Commnet.CommentService;
import com.travel.web_oasis.web.dto.PageRequestDTO;
import com.travel.web_oasis.web.dto.PageResultDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/comment")
@Controller
public class CommentController {

    private final CommentService commentService;
    @ResponseBody
    @PostMapping("/add")
    public Long addComment(@RequestBody CommentDTO commentDTO) {
        return commentService.addComment(commentDTO);
    }

    @ResponseBody
    @GetMapping("/{postId}")
    public PageResultDTO<CommentDTO, Comment> getComments(@PathVariable Long postId, PageRequestDTO pageRequestDTO) {

        return commentService.getCommentList(postId, pageRequestDTO);
    }
}
