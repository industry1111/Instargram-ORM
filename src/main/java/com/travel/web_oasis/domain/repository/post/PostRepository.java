package com.travel.web_oasis.domain.repository.post;

import com.travel.web_oasis.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface PostRepository extends JpaRepository<Post, Long>, QuerydslPredicateExecutor<Post> , CustomPostRepository{

}



