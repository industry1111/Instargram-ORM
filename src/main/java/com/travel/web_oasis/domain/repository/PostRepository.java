package com.travel.web_oasis.domain.repository;

import com.travel.web_oasis.domain.entity.Post;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;


public interface PostRepository extends JpaRepository<Post, Long>, QuerydslPredicateExecutor<Post> {


}



