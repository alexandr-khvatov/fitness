package com.kh.fitness.service;

import com.kh.fitness.entity.TrainingProgram;
import com.kh.fitness.repository.TrainingProgramRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TrainingProgramService {
    private final TrainingProgramRepository trainingProgramRepository;

    public Optional<TrainingProgram> findById(Long id) {
        return trainingProgramRepository.findById(id);
    }

    public TrainingProgram create(TrainingProgram training) {
        return Optional.of(training)
                .map(trainingProgramRepository::saveAndFlush)
                .orElseThrow();
    }

    public Boolean delete(Long id) {
        return trainingProgramRepository.findById(id)
                .map(entity -> {
                    trainingProgramRepository.delete(entity);
                    trainingProgramRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}