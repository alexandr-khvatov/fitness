package com.kh.fitness.repository;

import com.kh.fitness.entity.TrainingProgram;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProgramRepository extends JpaRepository<TrainingProgram, Long> {
    List<TrainingProgram> findAllByGymId(Long gymId);
}