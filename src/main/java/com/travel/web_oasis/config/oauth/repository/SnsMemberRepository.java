package com.travel.web_oasis.config.oauth.repository;

import com.travel.web_oasis.config.oauth.domain.SnsMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SnsMemberRepository extends JpaRepository<SnsMember, Long> {
    Optional<SnsMember> findByEmail(String email);
}
