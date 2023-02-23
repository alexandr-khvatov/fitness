package com.kh.fitness.repository;

import com.kh.fitness.entity.SubTrainingProgram;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubProgramRepository extends JpaRepository<SubTrainingProgram, Long> {
    List<SubTrainingProgram> findAllByTrainingProgramId(Long TrainingProgramId);
}