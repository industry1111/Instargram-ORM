package com.travel.web_oasis.domain.repository.follow;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.travel.web_oasis.domain.entity.Follow;
import com.travel.web_oasis.domain.entity.QFollow;
import com.travel.web_oasis.domain.repository.follow.CustomFollowRepository;
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
    public List<Long> getFollower(Long toMemberId) {
        QFollow follow = QFollow.follow;

        return queryFactory
                .select(follow.fromMember.id)
                .from(follow)
                .where(follow.toMember.id.eq(toMemberId))
                .fetch();
    }


    @Override
    public List<Long> getFollowing(Long fromMemberId) {
        QFollow follow = QFollow.follow;

        return queryFactory
                .select(follow.toMember.id)
                .from(follow)
                .where(follow.fromMember.id.eq(fromMemberId))
                .fetch();


    }
}
