package com.travel.web_oasis.domain.repository.member;

import com.travel.web_oasis.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>, QuerydslPredicateExecutor<Member>, CustomMemberRepository {

    Member findByEmail(String email);

    Member findByEmailAndProvider(String email, String provider);

    Member findByIdAndProvider(Long id, String provider);

}