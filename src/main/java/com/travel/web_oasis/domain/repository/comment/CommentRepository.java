package com.travel.web_oasis.domain.repository.comment;

import com.travel.web_oasis.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long>, CustomCommentRepository {
}
