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
import com.kh.fitness.repository.GymRepository;
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
    private final GymRepository gymRepository;

    public Optional<TrainingReadDto> findById(Long id) {
        return trainingRepository.findById(id)
                .map(trainingReadMapper::toDto);
    }

    public List<TrainingReadDto> findAllByGymId(Long gymId) {
        return trainingRepository.findAllByGymId(gymId).stream()
                .map(trainingReadMapper::toDto).toList();
    }

    public TrainingReadDto create(TrainingCreateDto training) {
        if (!training.getStart().isBefore(training.getEnd())) {
            throw new IncorrectRange("Неверный диапазон времени");
        }

        var gym = gymRepository.findById(1L).orElseThrow();
        var workingDays = gym.getOpeningHours();
        var day = workingDays.stream().filter(x -> x.getDayOfWeek().getValue() == training.getDayOfWeek()).findFirst();
        if (day.isPresent()) {
            var dayPresent = day.get();
            if (Boolean.FALSE.equals(dayPresent.getIsOpen())) {
                throw new IncorrectRange("Не рабочий день!");
            }
            if (!(training.getStart().compareTo(dayPresent.getStartTime()) >= 0 &&
                  training.getEnd().compareTo(dayPresent.getEndTime()) <= 0)) {
                throw new IncorrectRange("Неверный диапазон времени, режим работы в этот день: " + dayPresent.getStartTime() + "-" + dayPresent.getEndTime());
            }
        }

        return Optional.of(training)
                .map(trainingCreateMapper::toEntity)
                .map(trainingRepository::saveAndFlush)
                .map(trainingReadMapper::toDto)
                .orElseThrow();
    }

    @Transactional
    public Optional<TrainingReadDto> update(Long id, @Valid TrainingEditDto training) {
        if (!training.getStart().isBefore(training.getEnd())) {
            throw new IncorrectRange("Неверный диапазон времени");
        }
        var gym = gymRepository.findById(1L).orElseThrow();
        var workingDays = gym.getOpeningHours();
        var day = workingDays.stream().filter(x -> x.getDayOfWeek().getValue() == training.getDayOfWeek()).findFirst();
        if (day.isPresent()) {
            var dayPresent = day.get();
            if (Boolean.FALSE.equals(dayPresent.getIsOpen())) {
                throw new IncorrectRange("Не рабочий день!");
            }
            if (!(training.getStart().compareTo(dayPresent.getStartTime()) >= 0 &&
                  training.getEnd().compareTo(dayPresent.getEndTime()) <= 0)) {
                throw new IncorrectRange("Неверный диапазон времени, режим работы в этот день: " + dayPresent.getStartTime() + "-" + dayPresent.getEndTime());
            }
        }
        var freePass = freePassRepository.findAllByTrainingId(id).stream()
                .filter(t -> LocalDateTime.of(t.getDate(), t.getEndTime()).isAfter(LocalDateTime.now()) && !t.getIsDone())
                .count();
        if (freePass > 0) {
            throw new UnableToDeleteObjectContainsNestedObjects("Не возможно изменить, тренеровка закреплена за " + freePass + " посетителем(ями), замените у записавшихся тренеровку в разделе \"Запросы\", затем попробуйте снова ");
        }


        return trainingRepository.findById(id)
                .map(entity -> trainingEditMapper.updateEntity(training, entity))
                .map(trainingRepository::saveAndFlush)
                .map(trainingReadMapper::toDto);
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