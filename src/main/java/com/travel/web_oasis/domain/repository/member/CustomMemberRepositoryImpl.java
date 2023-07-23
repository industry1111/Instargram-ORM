package com.travel.web_oasis.domain.repository.member;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.SubQueryExpression;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.travel.web_oasis.domain.entity.QFollow;
import com.travel.web_oasis.domain.entity.QPost;
import com.travel.web_oasis.domain.member.Member;
import com.travel.web_oasis.domain.member.QMember;
import com.travel.web_oasis.web.dto.MemberDTO;
import com.travel.web_oasis.web.dto.QMemberDTO;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.lang.invoke.MethodHandles;
import java.util.List;

public class CustomMemberRepositoryImpl extends QuerydslRepositorySupport implements CustomMemberRepository {

    private final JPQLQueryFactory queryFactory;

    public CustomMemberRepositoryImpl(EntityManager entityManager) {
        super(Member.class);
        this.queryFactory = new JPAQueryFactory(entityManager);
    }


    @Override
    public List<MemberDTO> getMemberList(List<Long> membersIds, Long myId) {
        QMember member = new QMember("member");
        QFollow follow = QFollow.follow;
        return queryFactory
                .select(new QMemberDTO(member.id,
                        member.name,
                        member.picture,
                        member.introduction,
                        member.eq(member))
                        )
                .from(member)
                .where(member.id.notIn(membersIds)
                        .and(member.id.notIn(
                                JPAExpressions
                                        .select(follow.toMember.id)
                                        .from(follow)
                                        .where(follow.fromMember.id.in(myId))
                        )))
                .offset(0)
                .limit(5)
                .fetch();
    }

    @Override
    public MemberDTO getMemberProfile(Long memberId) {

        QMember member = QMember.member;
        QPost post = QPost.post;
        QFollow follow = QFollow.follow;

        SubQueryExpression<Long> postSizeSubQuery = JPAExpressions
                .select(post.count())
                .from(post)
                .where(post.member.id.eq(memberId));

        SubQueryExpression<Long> followerSizeSubQuery = JPAExpressions
                .select(follow.count())
                .from(follow)
                .where(follow.toMember.id.eq(memberId));

        SubQueryExpression<Long> followingSizeSubQuery = JPAExpressions
                .select(follow.count())
                .from(follow)
                .where(follow.fromMember.id.eq(memberId));

        Tuple result = queryFactory
                .select(member.id, member.name, member.email, member.introduction,
                        member.picture, postSizeSubQuery, followerSizeSubQuery, followingSizeSubQuery)
                .from(member)
                .where(member.id.eq(memberId))
                .fetchOne();

        return MemberDTO.builder()
                .id(result.get(member.id))
                .name(result.get(member.name))
                .email(result.get(member.email))
                .introduction(result.get(member.introduction))
                .picture(result.get(member.picture))
                .postSize(result.get(5,Long.class))
                .followersSize(result.get(6,Long.class))
                .followingSize(result.get(7, Long.class))
                .build();
    }
}
