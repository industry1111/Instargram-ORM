package com.travel.web_oasis.domain.repository.comment;

import com.travel.web_oasis.domain.entity.Comment;
import com.travel.web_oasis.domain.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface CommentRepository extends JpaRepository<Comment,Long>, QuerydslPredicateExecutor<Comment>,CustomCommentRepository {
}
