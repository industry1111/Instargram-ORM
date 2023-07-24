package com.travel.web_oasis.domain.repository.post;

import com.travel.web_oasis.domain.entity.Post;
import com.travel.web_oasis.web.dto.PostDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomPostRepository {

    Page<Post> getPostList(Pageable pageable, Long memberId);
    Page<Post> gePostsByMemberId(Pageable pageable, Long memberId);

}
