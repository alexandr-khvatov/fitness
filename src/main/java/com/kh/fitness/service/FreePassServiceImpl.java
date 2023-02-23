package com.kh.fitness.service;

import com.kh.fitness.dto.free_pass.FreePassCreateDto;
import com.kh.fitness.dto.free_pass.FreePassEditTrainingDto;
import com.kh.fitness.dto.free_pass.FreePassReadDto;
import com.kh.fitness.exception.EmailAlreadyExistException;
import com.kh.fitness.exception.NegativeDataException;
import com.kh.fitness.exception.PhoneAlreadyExistException;
import com.kh.fitness.exception.UnableToDeleteObjectContainsNestedObjects;
import com.kh.fitness.mapper.free_pass.FreePassCreateMapper;
import com.kh.fitness.mapper.free_pass.FreePassEditTrainingMapper;
import com.kh.fitness.mapper.free_pass.FreePassReadDtoMapper;
import com.kh.fitness.repository.FreePassRepository;
import com.kh.fitness.repository.TrainingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FreePassServiceImpl {
    private final EmailService emailService;
    private final TrainingServiceImpl trainingServiceImpl;
    private final TrainingRepository trainingRepository;
    private final FreePassRepository freePassRepository;
    private final FreePassReadDtoMapper freePassReadDtoMapper;
    private final FreePassCreateMapper freePassCreateMapper;
    private final FreePassEditTrainingMapper freePassEditTrainingMapper;

    public Optional<FreePassReadDto> findById(Long id) {
        return freePassRepository.findById(id).map(freePassReadDtoMapper::toDto);
    }

    public List<FreePassReadDto> findAllByGymId(Long gymId) {
        return freePassRepository.findAllByGymIdOrderByIsDone(gymId).stream()
                .map(freePassReadDtoMapper::toDto).toList();
    }

    private Boolean isRange(LocalTime start, LocalTime end, LocalTime target) {
        return target.isAfter(start.minusMinutes(5)) && target.isBefore(end.plusMinutes(5));
    }

    @Transactional
    public FreePassReadDto create(FreePassCreateDto dto) {
        var trainings = trainingServiceImpl.findAllByGymId(dto.getGymId());
        DayOfWeek dayOfWeek = dto.getDate().getDayOfWeek();
        var training = trainings.stream()
                .filter(t -> t.getDayOfWeek()
                                     .equals(dayOfWeek.getValue()) &&
                             (isRange(t.getStart(), t.getEnd(), dto.getStart()))).findAny();

        var freePass = Optional.<FreePassReadDto>empty();
        StringBuilder message = new StringBuilder();
        if (training.isPresent()) {


            // check the existence of other freePass with this phone
            if (!Optional.ofNullable(dto.getPhone())
                    .map(freePassRepository::existsUserByPhone)
                    .map(exist -> !exist)
                    .orElse(false)) {
                throw new PhoneAlreadyExistException("Обращение уже обработывается!", new Object());
            }
            // check the existence of other freePass with this email
            if (!Optional.ofNullable(dto.getEmail())
                    .map(freePassRepository::existsUserByEmail)
                    .map(exist -> !exist)
                    .orElse(false)) {
                throw new EmailAlreadyExistException("Обращение уже обработывается!", new Object());
            }

            freePass = Optional.of(dto)
                    .map(freePassCreateMapper::toEntity)
                    .map(freePassRepository::saveAndFlush)
                    .map(freePassReadDtoMapper::toDto);
            message.append("Вы записаны на занятие :")
                    .append(training.get().getSubProgram())
                    .append(" Время: ")
                    .append(training.get().getStart())
                    .append("-")
                    .append(training.get().getEnd())
                    .append(" " + dto.getDate());
        } else {
            message.append("К сожалению в выбранное вами время в клубе нет занятий. Мы выслали вам расписание");
        }
        message.append("Расписание занятий! " + "http://localhost:3000/schedule ");
        emailService.sendSimpleEmail(dto.getEmail(), "ПРОБНОЕ ЗАНЯТИЕ ", message.toString());
        return freePass.get();
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

    boolean isWithinRange(LocalDateTime testDate) {
        return !(testDate.isBefore(LocalDateTime.now()) || testDate.isAfter(LocalDateTime.now()));
    }

    @Transactional
    public Optional<FreePassReadDto> changeTraining(Long id, FreePassEditTrainingDto dto) {
        var newTraining = trainingRepository.findById(dto.getTrainingId()).orElseThrow();
        var newDateTime = LocalDateTime.of(dto.getDate(), newTraining.getStartTime());
        if (!newDateTime.isAfter(LocalDateTime.now())) {
            throw new NegativeDataException("Неверная дата");
        }
        var maybe = freePassRepository.findById(id)
                .map(entity -> freePassEditTrainingMapper.updateEntity(dto, entity))
                .map(freePassRepository::saveAndFlush)
                .map(freePassReadDtoMapper::toDto).orElseThrow();
        StringBuilder message = new StringBuilder();
        message.append("Ваше пробное занятие перенесено :")
                .append(newTraining.getSubTrainingProgram().getName())
                .append(" Время: ")
                .append(newTraining.getStartTime())
                .append("-")
                .append(newTraining.getEndTime()).append(" ").append(dto.getDate());
        emailService.sendSimpleEmail(maybe.getEmail(), "ПРОБНОЕ ЗАНЯТИЕ ", message.toString());
        return Optional.ofNullable(maybe);
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
