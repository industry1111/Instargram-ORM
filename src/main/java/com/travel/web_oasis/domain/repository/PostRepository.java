package com.travel.web_oasis.domain.repository;

import com.travel.web_oasis.domain.posts.Post;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PostRepository extends JpaRepository<Post, Long> {

}


