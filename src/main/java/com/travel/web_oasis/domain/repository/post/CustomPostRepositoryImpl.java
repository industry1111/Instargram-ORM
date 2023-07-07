package com.travel.web_oasis.domain.repository.post;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.travel.web_oasis.domain.entity.Follow;
import com.travel.web_oasis.domain.entity.Post;
import com.travel.web_oasis.domain.entity.QFollow;
import com.travel.web_oasis.domain.entity.QPost;
import com.travel.web_oasis.domain.member.QMember;
import com.travel.web_oasis.domain.repository.member.CustomMemberRepository;
import com.travel.web_oasis.web.dto.MemberDTO;
import com.travel.web_oasis.web.dto.PageResultDTO;
import com.travel.web_oasis.web.dto.PostDTO;
import com.travel.web_oasis.web.dto.QMemberDTO;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.io.File;
import java.util.List;

public class CustomPostRepositoryImpl extends QuerydslRepositorySupport implements CustomPostRepository {
    private final JPQLQueryFactory queryFactory;


    public CustomPostRepositoryImpl(JPQLQueryFactory queryFactory) {
        super(Post.class);
        this.queryFactory = queryFactory;
    }

//    @Override
//    public Page<Post> getMemberPostList(Pageable pageable, Long memberId) {
//        QPost post = new QPost("post");
//        QueryResults<Post> results = queryFactory
//                .selectFrom(post)
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .fetchResults();
//
//        List<Post> posts = results.getResults();
//        long total = results.getTotal();
//
//    }
}
