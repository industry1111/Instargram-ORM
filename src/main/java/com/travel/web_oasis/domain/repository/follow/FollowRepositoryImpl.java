package com.travel.web_oasis.domain.repository.follow;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.SubQueryExpression;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.travel.web_oasis.domain.entity.Follow;
import com.travel.web_oasis.domain.entity.QFollow;
import com.travel.web_oasis.domain.member.QMember;
import com.travel.web_oasis.domain.repository.follow.CustomFollowRepository;
import com.travel.web_oasis.web.dto.MemberDTO;
import com.travel.web_oasis.web.dto.QMemberDTO;
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
    public Long deleteFollow(Long toMemberId, Long fromMemberId) {

        QFollow follow = QFollow.follow;

        BooleanExpression expression = follow.toMember.id.eq(toMemberId)
                                            .and(follow.fromMember.id.eq(fromMemberId));

        return delete(follow)
                .where(expression).execute();
    }

    @Override
    public List<MemberDTO> getFollowings(Long memberId) {
        QFollow follow = new QFollow("follow");
        QMember member = new QMember("member");

        return queryFactory
                .select(new QMemberDTO(
                        follow.toMember.id,
                        follow.toMember.name,
                        follow.toMember.picture,
                        follow.toMember.introduction,
                        follow.toMember.eq(follow.toMember)
                ))
                .from(follow)
                .where(follow.fromMember.id.eq(memberId))
                .fetch();
    }


    @Override
    public List<MemberDTO> getFollowers(Long memberId) {
        QFollow follow = new QFollow("follow");
        QMember member = new QMember("member");

        return queryFactory
                .select(new QMemberDTO(
                        follow.fromMember.id,
                        follow.fromMember.name,
                        follow.fromMember.picture,
                        follow.fromMember.introduction,
                        follow.fromMember.id.in(
                                JPAExpressions.select(follow.toMember.id)
                                        .from(follow)
                                        .where(follow.fromMember.id.eq(memberId))
                        )
                ))
                .from(follow)
                .where(follow.toMember.id.eq(memberId))
                .fetch();
    }
}
