package com.kh.fitness.repository;

import com.kh.fitness.entity.Gym;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GymRepository extends JpaRepository<Gym, Long> {
    List<Gym> findAll();

    @Override
    Optional<Gym> findById(Long id);

    Optional<Gym> findByName(String name);
}
