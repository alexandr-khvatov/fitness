package com.kh.fitness.service;

import com.kh.fitness.dto.training.TrainingCreateDto;
import com.kh.fitness.dto.training.TrainingEditDto;
import com.kh.fitness.dto.training.TrainingReadDto;
import com.kh.fitness.mapper.training.TrainingCreateMapper;
import com.kh.fitness.mapper.training.TrainingEditMapper;
import com.kh.fitness.mapper.training.TrainingReadMapper;
import com.kh.fitness.repository.TrainingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TrainingService {
    private final TrainingRepository trainingRepository;
    private final TrainingReadMapper trainingReadMapper;
    private final TrainingCreateMapper trainingCreateMapper;
    private final TrainingEditMapper trainingEditMapper;

    public Optional<TrainingReadDto> findById(Long id) {
        return trainingRepository.findById(id)
                .map(trainingReadMapper::map);
    }

    public List<TrainingReadDto> findAllByGymId(Long gymId) {
        return trainingRepository.findAllByGymId(gymId).stream()
                .map(trainingReadMapper::map).toList();
    }

    public TrainingReadDto create(TrainingCreateDto training) {
        return Optional.of(training)
                .map(trainingCreateMapper::map)
                .map(trainingRepository::saveAndFlush)
                .map(trainingReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<TrainingReadDto> update(Long id, @Valid TrainingEditDto training) {
        return trainingRepository.findById(id)
                .map(entity -> trainingEditMapper.map(training, entity))
                .map(trainingRepository::saveAndFlush)
                .map(trainingReadMapper::map);
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