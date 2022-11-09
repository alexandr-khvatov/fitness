package com.kh.fitness.repository;

import com.kh.fitness.entity.Coach;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CoachRepository extends JpaRepository<Coach, Long> {

    List<Coach> findAllByGymId(Long gymId);
}