package com.travel.web_oasis.domain.repository.post;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQueryFactory;
import com.travel.web_oasis.domain.entity.Post;
import com.travel.web_oasis.domain.entity.QFollow;
import com.travel.web_oasis.domain.entity.QPost;
import com.travel.web_oasis.domain.member.QMember;
import com.travel.web_oasis.web.dto.*;
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
        QMember member = new QMember("member");
        QFollow follow = new QFollow("follow");

        QueryResults<Post> results = queryFactory.selectFrom(post)
                .where(
                        post.member.id.eq(memberId)
                                .or(post.member.id.in(
                                                JPAExpressions
                                                        .select(
                                                                follow.toMember.id
                                                        )
                                                        .from(follow)
                                                        .where(follow.fromMember.id.eq(memberId))
                                        )
                                ))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<Post> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content,pageable,total);
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
