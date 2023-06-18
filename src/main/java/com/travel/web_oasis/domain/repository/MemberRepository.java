package com.travel.web_oasis.domain.repository;

import com.travel.web_oasis.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    void deleteByName(String name);
}