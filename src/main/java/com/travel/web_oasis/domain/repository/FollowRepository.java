package com.travel.web_oasis.domain.repository;

import com.querydsl.core.BooleanBuilder;
import com.travel.web_oasis.domain.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface FollowRepository extends JpaRepository<Follow,Long>, QuerydslPredicateExecutor<Follow> ,CustomFollowRepository{

}
