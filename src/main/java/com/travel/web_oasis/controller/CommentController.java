package com.travel.web_oasis.controller;

import com.travel.web_oasis.domain.entity.Comment;
import com.travel.web_oasis.web.dto.CommentDTO;
import com.travel.web_oasis.domain.service.Commnet.CommentService;
import com.travel.web_oasis.web.dto.PageRequestDTO;
import com.travel.web_oasis.web.dto.PageResultDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/comment")
@Controller
public class CommentController {

    private final CommentService commentService;
    @ResponseBody
    @PostMapping("/add")
    public Long addComment(@RequestBody CommentDTO commentDTO) {
        System.out.println("commentDTO = " + commentDTO);
        return commentService.addComment(commentDTO);
    }

    @ResponseBody
    @GetMapping("/{postId}")
    public List<CommentDTO> getComments(@PathVariable Long postId) {

        return commentService.getCommentList(postId);
    }
}
