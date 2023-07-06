package com.travel.web_oasis.domain.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.NumberTemplate;
import com.querydsl.jpa.JPQLQueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.travel.web_oasis.domain.entity.Follow;
import com.travel.web_oasis.domain.entity.QFollow;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class FollowRepositoryImpl extends QuerydslRepositorySupport implements CustomFollowRepository {

    private final JPQLQueryFactory queryFactory;

    public FollowRepositoryImpl(EntityManager entityManager) {
        super(Follow.class);
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Transactional
    @Override
    public void deleteFollow(Long toMemberId, Long fromMemberId) {

        QFollow follow = QFollow.follow;

        BooleanExpression expression = follow.toMember.id.eq(toMemberId)
                                            .and(follow.fromMember.id.eq(fromMemberId));

        delete(follow)
                .where(expression).execute();


    }

    @Override
    public Long getFollowingCnt(Long toMemberId) {
        QFollow follow = QFollow.follow;

        return queryFactory
                .select(follow.count())
                .from(follow)
                .where(follow.toMember.id.eq(toMemberId))
                .fetchFirst();
    }


    @Override
    public Long getFollowerCnt(Long fromMemberId) {
        QFollow follow = QFollow.follow;

        return queryFactory
                .select(follow.count())
                .from(follow)
                .where(follow.fromMember.id.eq(fromMemberId))
                .fetchFirst();


    }
}
