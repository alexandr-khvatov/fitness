package com.kh.fitness.service;

import com.kh.fitness.entity.Training;
import com.kh.fitness.repository.TrainingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TrainingService {
    private final TrainingRepository trainingRepository;

    public Optional<Training> findById(Long id) {
        return trainingRepository.findById(id);
    }

    public Training create(Training training) {
        return Optional.of(training)
                .map(trainingRepository::saveAndFlush)
                .orElseThrow();
    }

    public Boolean delete(Long id) {
        return trainingRepository.findById(id)
                .map(entity -> {
                    trainingRepository.delete(entity);
                    trainingRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}