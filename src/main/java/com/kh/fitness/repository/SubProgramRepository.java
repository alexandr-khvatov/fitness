package com.kh.fitness.repository;

import com.kh.fitness.entity.SubTrainingProgram;
import com.kh.fitness.entity.TrainingProgram;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubProgramRepository extends JpaRepository<SubTrainingProgram, Long> {
    // TODO пофиксить n+1
    List<SubTrainingProgram> findAllByTrainingProgramId(Long TrainingProgramId);
}