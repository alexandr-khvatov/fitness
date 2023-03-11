package com.kh.fitness.repository;

import com.kh.fitness.entity.TrainingProgram;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ProgramRepository extends
        JpaRepository<TrainingProgram, Long>,
        QuerydslPredicateExecutor<TrainingProgram> {

    @Override
    Page<TrainingProgram> findAll(Predicate predicate, Pageable pageable);
}