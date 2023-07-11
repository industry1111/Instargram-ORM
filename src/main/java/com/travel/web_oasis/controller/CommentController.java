package com.travel.web_oasis.controller;

import com.travel.web_oasis.domain.service.Commnet.CommentDTO;
import com.travel.web_oasis.domain.service.Commnet.CommentService;
import lombok.RequiredArgsConstructor;
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
        System.out.println("commentDTO = " + commentDTO);

        return commentService.addComment(commentDTO);
    }
}
