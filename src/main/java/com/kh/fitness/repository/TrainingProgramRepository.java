package com.kh.fitness.repository;

import com.kh.fitness.entity.TrainingProgram;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainingProgramRepository extends JpaRepository<TrainingProgram, Long> {
    // TODO пофиксить n+1
    List<TrainingProgram> findAllByGymId(Long gymId);
}