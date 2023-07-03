package com.travel.web_oasis.domain.repository;

import com.travel.web_oasis.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByEmail(String email);

    Member findByEmailAndProvider(String email, String provider);
}