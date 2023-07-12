package com.travel.web_oasis.domain.repository.post;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.JPQLQueryFactory;
import com.travel.web_oasis.domain.entity.Post;
import com.travel.web_oasis.domain.entity.QPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class CustomPostRepositoryImpl extends QuerydslRepositorySupport implements CustomPostRepository {
    private final JPQLQueryFactory queryFactory;


    public CustomPostRepositoryImpl(JPQLQueryFactory queryFactory) {
        super(Post.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<Post> getPostList(Pageable pageable, Long memberId) {
        QPost post = new QPost("post");

        return null;
    }

    @Override
    public Page<Post> gePostsByMemberId(Pageable pageable, Long memberId) {
        QPost post = new QPost("post");
        QueryResults<Post> results = queryFactory
                .selectFrom(post)
                .where(post.member.id.eq(memberId))
                .fetchResults();

        List<Post> content = results.getResults();
        long total = results.getTotal();

        return  new PageImpl<>(content,pageable,total);
    }

}
