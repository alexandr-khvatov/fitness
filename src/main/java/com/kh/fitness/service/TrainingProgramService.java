package com.kh.fitness.service;

import com.kh.fitness.dto.trainingProgram.TrainingProgramReadDto;
import com.kh.fitness.entity.TrainingProgram;
import com.kh.fitness.mapper.trainingProgram.TrainingProgramReadDtoMapper;
import com.kh.fitness.repository.TrainingProgramRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TrainingProgramService {
    private final TrainingProgramRepository trainingProgramRepository;
    private final TrainingProgramReadDtoMapper trainingProgramReadDtoMapper;

    public Optional<TrainingProgram> findById(Long id) {
        return trainingProgramRepository.findById(id);
    }

    public List<TrainingProgramReadDto> findAllByGymId(Long gymId) {
        return trainingProgramRepository.findAllByGymId(gymId).stream()
                .map(trainingProgramReadDtoMapper::map).toList();
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