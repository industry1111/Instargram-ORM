package com.travel.web_oasis.domain.service.Commnet;

import com.travel.web_oasis.domain.entity.Comment;
import com.travel.web_oasis.web.dto.CommentDTO;
import com.travel.web_oasis.web.dto.PageRequestDTO;
import com.travel.web_oasis.web.dto.PageResultDTO;

import java.util.List;

public interface CommentService {

    Long addComment(CommentDTO commentDTO);

    List<CommentDTO> getCommentList(Long postId);
}
