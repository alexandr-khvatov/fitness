package com.kh.fitness.repository;

import com.kh.fitness.entity.Coach;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CoachRepository extends JpaRepository<Coach, Long> {

    List<Coach> findAllByGymId(Long gymId);

    Optional<Coach> findByEmailIgnoreCase(String email);

    Optional<Coach> findByPhone(String phone);
}