package com.travel.web_oasis.domain.service.Commnet;

import com.travel.web_oasis.domain.entity.Comment;
import com.travel.web_oasis.domain.member.Member;
import com.travel.web_oasis.web.dto.PageRequestDTO;
import com.travel.web_oasis.web.dto.PageResultDTO;
import com.travel.web_oasis.web.dto.PostDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface CommentService {

    Long addComment(CommentDTO commentDTO);

    PageResultDTO<CommentDTO, Comment> getCommentList(Long postId, PageRequestDTO pageRequestDTO);
}
