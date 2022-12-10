package com.kh.fitness.repository;

import com.kh.fitness.entity.Training;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainingRepository extends JpaRepository<Training, Long> {
    List<Training> findAllByGymId(Long gymId);
}