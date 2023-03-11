package com.kh.fitness.repository;

import com.kh.fitness.entity.Coach;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;

public interface CoachRepository extends
        JpaRepository<Coach, Long>,
        QuerydslPredicateExecutor<Coach> {

    List<Coach> findAllByGymId(Long gymId);

    Optional<Coach> findByEmailIgnoreCase(String email);

    Optional<Coach> findByPhone(String phone);

    @Override
    Page<Coach> findAll(Predicate predicate, Pageable pageable);
}