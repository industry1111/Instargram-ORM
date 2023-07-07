package com.travel.web_oasis.domain.repository.post;

import com.travel.web_oasis.domain.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomPostRepository {
    Page<Post> getMemberPostList(Pageable pageable, Long memberId);

}
