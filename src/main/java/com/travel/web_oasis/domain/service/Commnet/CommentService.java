package com.travel.web_oasis.domain.service.Commnet;

import com.travel.web_oasis.domain.entity.Comment;
import com.travel.web_oasis.web.dto.CommentDTO;
import com.travel.web_oasis.web.dto.PageRequestDTO;
import com.travel.web_oasis.web.dto.PageResultDTO;

public interface CommentService {

    Long addComment(CommentDTO commentDTO);

    PageResultDTO<CommentDTO, Comment> getCommentList(Long postId, PageRequestDTO pageRequestDTO);
}
