package com.kh.fitness.service;

import com.kh.fitness.dto.training.TrainingCreateDto;
import com.kh.fitness.dto.training.TrainingEditDto;
import com.kh.fitness.dto.training.TrainingReadDto;
import com.kh.fitness.exception.IncorrectRange;
import com.kh.fitness.exception.UnableToDeleteObjectContainsNestedObjects;
import com.kh.fitness.mapper.training.TrainingCreateMapper;
import com.kh.fitness.mapper.training.TrainingEditMapper;
import com.kh.fitness.mapper.training.TrainingReadMapper;
import com.kh.fitness.repository.FreePassRepository;
import com.kh.fitness.repository.SubProgramRepository;
import com.kh.fitness.repository.TrainingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Validated
public class TrainingService {
    private final TrainingRepository trainingRepository;
    private final TrainingReadMapper trainingReadMapper;
    private final TrainingCreateMapper trainingCreateMapper;
    private final TrainingEditMapper trainingEditMapper;
    private final FreePassRepository freePassRepository;
    private final SubProgramRepository subProgramRepository;

    public Optional<TrainingReadDto> findById(Long id) {
        return trainingRepository.findById(id)
                .map(trainingReadMapper::map);
    }

    public List<TrainingReadDto> findAllByGymId(Long gymId) {
        return trainingRepository.findAllByGymId(gymId).stream()
                .map(trainingReadMapper::map).toList();
    }

    public TrainingReadDto create(TrainingCreateDto training) {
        if (!training.getStart().isBefore(training.getEnd())) {
            throw new IncorrectRange("Неверный диапазон времени");
        }
        return Optional.of(training)
                .map(trainingCreateMapper::map)
                .map(trainingRepository::saveAndFlush)
                .map(trainingReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<TrainingReadDto> update(Long id, @Valid TrainingEditDto training) {
        if (!training.getStart().isBefore(training.getEnd())) {
            throw new IncorrectRange("Неверный диапазон времени");
        }
//        var maybeSubTraining = subProgramRepository.findById(training.getSubProgramId()).orElseThrow();
//        var message = new StringBuilder();
//        var maybeTraining = trainingRepository.findById(id).orElseThrow();
        var freePass = freePassRepository.findAllByTrainingId(id).stream()
                .filter(t -> LocalDateTime.of(t.getDate(), t.getEndTime()).isAfter(LocalDateTime.now()) && !t.getIsDone())
                .count();
        if (freePass > 0) {
            throw new UnableToDeleteObjectContainsNestedObjects("Не возможно изменить, тренеровка закреплена за " + freePass + " посетителем(ями), замените у записавшихся тренеровку в разделе \"Запросы\", затем попробуйте снова ");
        }


        return trainingRepository.findById(id)
                .map(entity -> trainingEditMapper.map(training, entity))
                .map(trainingRepository::saveAndFlush)
                .map(trainingReadMapper::map);
    }

    public Boolean delete(Long id) {
        var maybeTraining = trainingRepository.findById(id).orElseThrow();
        var freePass = freePassRepository.findAllByTrainingId(id).stream()
                .filter(t -> LocalDateTime.of(t.getDate(), t.getEndTime()).isAfter(LocalDateTime.now()) && !t.getIsDone())
                .findAny();

        freePass.ifPresent((ignore) -> {
            throw new UnableToDeleteObjectContainsNestedObjects("Не возможно удалить, тренеровка закреплена за посетителем, замените у записавшихся тренеровку в разделе \"Запросы\", затем попробуйте снова ");
        });

        return trainingRepository.findById(id)
                .map(entity -> {
                    trainingRepository.delete(entity);
                    trainingRepository.flush();
                    return true;
                })
                .orElse(false);

    }
}