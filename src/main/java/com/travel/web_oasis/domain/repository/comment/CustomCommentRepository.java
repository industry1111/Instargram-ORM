package com.travel.web_oasis.domain.repository.comment;

import com.travel.web_oasis.web.dto.CommentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomCommentRepository {

    Page<CommentDTO> getCommentsByPostId(Pageable pageable, Long postId);
}
