package com.travel.web_oasis.domain.repository.comment;

import com.travel.web_oasis.web.dto.CommentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomCommentRepository {

    List<CommentDTO> getCommentsByPostId(Long postId);
}
