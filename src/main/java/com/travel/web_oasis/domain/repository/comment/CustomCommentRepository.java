package com.travel.web_oasis.domain.repository.comment;

import com.travel.web_oasis.domain.service.Commnet.CommentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomCommentRepository {

    Page<CommentDTO> getCommentsByPostId(Pageable pageable, Long postId);
}
