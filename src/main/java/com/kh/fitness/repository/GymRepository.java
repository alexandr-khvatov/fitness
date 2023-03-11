package com.kh.fitness.repository;

import com.kh.fitness.entity.gym.Gym;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;

public interface GymRepository extends
        JpaRepository<Gym, Long>,
        QuerydslPredicateExecutor<Gym> {
    List<Gym> findAll();

    @Override
    Optional<Gym> findById(Long id);

    Optional<Gym> findByName(String name);

    @Override
    Page<Gym> findAll(Predicate predicate, Pageable pageable);
}
