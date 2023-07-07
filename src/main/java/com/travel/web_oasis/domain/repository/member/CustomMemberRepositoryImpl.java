package com.travel.web_oasis.domain.repository.member;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.travel.web_oasis.domain.entity.Follow;
import com.travel.web_oasis.domain.entity.QFollow;
import com.travel.web_oasis.domain.member.QMember;
import com.travel.web_oasis.web.dto.MemberDTO;
import com.travel.web_oasis.web.dto.QMemberDTO;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class CustomMemberRepositoryImpl extends QuerydslRepositorySupport implements CustomMemberRepository {

    private final JPQLQueryFactory queryFactory;

    public CustomMemberRepositoryImpl(EntityManager entityManager) {
        super(Follow.class);
        this.queryFactory = new JPAQueryFactory(entityManager);
    }


    @Override
    public List<MemberDTO> getMemberList(List<Long> membersIds, Long myId) {
        QMember member = new QMember("member");
        QFollow follow = QFollow.follow;

        return queryFactory
                .select(new QMemberDTO(member.id,
                        member.name,
                        member.picture))
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

}
