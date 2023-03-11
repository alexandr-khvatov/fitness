package com.kh.fitness.service.free_pass;

import com.kh.fitness.dto.free_pass.FreePassCreateDto;
import com.kh.fitness.dto.free_pass.FreePassEditDto;
import com.kh.fitness.dto.free_pass.FreePassReadDto;
import com.kh.fitness.entity.Training;
import com.kh.fitness.exception.NegativeDataException;
import com.kh.fitness.exception.UnableToDeleteObjectContainsNestedObjects;
import com.kh.fitness.mapper.free_pass.FreePassCreateMapper;
import com.kh.fitness.mapper.free_pass.FreePassEditTrainingMapper;
import com.kh.fitness.mapper.free_pass.FreePassReadDtoMapper;
import com.kh.fitness.repository.FreePassRepository;
import com.kh.fitness.repository.GymRepository;
import com.kh.fitness.repository.TrainingRepository;
import com.kh.fitness.service.TrainingServiceImpl;
import com.kh.fitness.service.email.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Validated
public class FreePassServiceImpl {
    private final EmailService emailService;
    private final TrainingServiceImpl trainingServiceImpl;
    private final TrainingRepository trainingRepository;
    private final GymRepository gymRepository;
    private final FreePassRepository freePassRepository;
    private final FreePassReadDtoMapper freePassReadDtoMapper;
    private final FreePassCreateMapper freePassCreateMapper;
    private final FreePassEditTrainingMapper freePassEditTrainingMapper;

    private static final String ERROR_MSG_REQ_ALREADY_PROCESSED = "Your request is already being processed";
    private static final String MSG_TRAINING_NOT_FOUND = "Training with id %s not found";
    private static final String MSG_GYM_NOT_FOUND = "Gym with id %s not found";

    public Optional<FreePassReadDto> findById(Long id) {
        return freePassRepository.findById(id).map(freePassReadDtoMapper::toDto);
    }

    public List<FreePassReadDto> findAllByGymId(Long gymId) {
        return freePassRepository.findAllByGymIdOrderByIsDone(gymId).stream()
                .map(freePassReadDtoMapper::toDto).toList();
    }

    private Boolean isRange(LocalTime start, LocalTime end, LocalTime target) {
        return target.compareTo(start) >= 0 && target.isBefore(end);
    }

    @Transactional
    public FreePassReadDto create(@Valid FreePassCreateDto dto) {
        var maybeGym = gymRepository.findById(dto.getGymId())
                .orElseThrow(() -> new NoSuchElementException(format(MSG_GYM_NOT_FOUND, dto.getGymId())));
        var maybeTraining = trainingRepository.findById(dto.getTrainingId())
                .orElseThrow(() -> new NoSuchElementException(format(MSG_TRAINING_NOT_FOUND, dto.getTrainingId())));

        checkExistFreePassByEmailAndPhone(dto.getEmail(), dto.getPhone());
        if (!isSyncedDates(maybeTraining, dto)) {
            throw new IllegalArgumentException("Invalid date or time");
        }

        var entity = freePassCreateMapper.toEntity(dto);
        entity = freePassRepository.saveAndFlush(entity);
        var freePass = freePassReadDtoMapper.toDto(entity);

        String message = FreePassMessageHelper.prepareSuccessMessage(
                maybeTraining.getSubTrainingProgram().getName(),
                maybeTraining.getDate(),
                maybeTraining.getStartTime(),
                maybeTraining.getEndTime());

        emailService.sendSimpleEmail(dto.getEmail(), "ПРОБНОЕ ЗАНЯТИЕ", message);
        return freePass;
    }

    private boolean isSyncedDates(Training training, FreePassCreateDto dto) {
        return (training.getDate() != null && training.getDate().isEqual(dto.getDate())
                && training.getStartTime().equals(dto.getStart())
                && training.getEndTime().equals(dto.getEnd()));
    }

    private void checkExistFreePassByEmailAndPhone(String email, String phone) {
        checkExistFreePassByEmail(email);
        checkExistFreePassByPhone(phone);
    }

    private void checkExistFreePassByEmail(String email) {
        if (freePassRepository.existsFreePassByEmail(email)) {
            throw new IllegalArgumentException(ERROR_MSG_REQ_ALREADY_PROCESSED);
        }
    }

    private void checkExistFreePassByPhone(String phone) {
        if (freePassRepository.existsFreePassByPhone(phone)) {
            throw new IllegalArgumentException(ERROR_MSG_REQ_ALREADY_PROCESSED);
        }
    }

    @Transactional
    public Optional<FreePassReadDto> updateFieldIsDone(Long id) {
        return freePassRepository.findById(id)
                .map(entity -> {
                    entity.setIsDone(!entity.getIsDone());
                    return entity;
                })
                .map(freePassRepository::saveAndFlush)
                .map(freePassReadDtoMapper::toDto);
    }

    @Transactional
    public FreePassReadDto changeTraining(Long id, FreePassEditDto dto) {
        var maybeGym = gymRepository.findById(dto.getGymId())
                .orElseThrow(() -> new NoSuchElementException(format(MSG_GYM_NOT_FOUND, dto.getGymId())));
        var newTraining = trainingServiceImpl.findById(dto.getTrainingId())
                .orElseThrow(() -> new NoSuchElementException(format(MSG_TRAINING_NOT_FOUND, dto.getTrainingId())));
        var newDateTime = LocalDateTime.of(newTraining.getDate(), newTraining.getStart());

        if (!(newDateTime.isEqual(dto.getDate()) && newDateTime.isAfter(LocalDateTime.now()))) {
            throw new NegativeDataException("Неверная дата");
        }

        var maybe = freePassRepository.findById(id)
                .map(entity -> freePassEditTrainingMapper.updateEntity(dto, entity))
                .map(freePassRepository::saveAndFlush)
                .map(freePassReadDtoMapper::toDto).orElseThrow();

        String message = FreePassMessageHelper.preparePostponementNoticeMessage(
                newTraining.getSubProgram(),
                newTraining.getDate(),
                newTraining.getStart(),
                newTraining.getEnd());

        emailService.sendSimpleEmail(maybe.getEmail(), "ПРОБНОЕ ЗАНЯТИЕ ", message);
        return maybe;
    }

    @Transactional
    public Boolean delete(Long id) {
        try {
            return freePassRepository.findById(id)
                    .map(entity -> {
                        freePassRepository.delete(entity);
                        freePassRepository.flush();
                        return true;
                    })
                    .orElse(false);
        } catch (Exception e) {
            throw new UnableToDeleteObjectContainsNestedObjects("Не возможно удалить, тренер закреплен за тренеровкой");
        }
    }
}
