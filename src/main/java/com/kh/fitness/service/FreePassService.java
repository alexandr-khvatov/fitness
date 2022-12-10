package com.kh.fitness.service;

import com.kh.fitness.dto.free_pass.FreePassCreateDto;
import com.kh.fitness.dto.free_pass.FreePassReadDto;
import com.kh.fitness.entity.FreePass;
import com.kh.fitness.mapper.free_pass.FreePassCreateMapper;
import com.kh.fitness.mapper.free_pass.FreePassReadDtoMapper;
import com.kh.fitness.repository.FreePassRepository;
import com.kh.fitness.repository.GymRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FreePassService {
    private final EmailService emailService;
    private final TrainingService trainingService;
    private final GymRepository gymRepository;
    private final FreePassRepository freePassRepository;
    private final FreePassReadDtoMapper freePassReadDtoMapper;
    private final FreePassCreateMapper freePassCreateMapper;

    public Optional<FreePass> findById(Long id) {
        return freePassRepository.findById(id);
    }

    public List<FreePassReadDto> findAllByGymId(Long gymId) {
        return freePassRepository.findAllByGymIdOrderByIsDone(gymId).stream()
                .map(freePassReadDtoMapper::map).toList();
    }

    private Boolean isRange(LocalTime start, LocalTime end, LocalTime target) {
        return target.isAfter(start.minusMinutes(5)) && target.isBefore(end.plusMinutes(5));
    }

    @Transactional
    public FreePassReadDto create(FreePassCreateDto dto) {
        var trainings = trainingService.findAllByGymId(dto.getGymId());
        DayOfWeek dayOfWeek = dto.getDate().getDayOfWeek();
        var training = trainings.stream()
                .filter(t -> t.getDayOfWeek()
                                     .equals(dayOfWeek.getValue()) &&
                             (isRange(t.getStart(), t.getEnd(), dto.getStart()))).findAny();

        var freePass = Optional.<FreePassReadDto>empty();
        StringBuilder message = new StringBuilder("Расписание занятий! " + "http://localhost:3000/schedule ");
        if (training.isPresent()) {
            freePass = Optional.of(dto)
                    .map(freePassCreateMapper::map)
                    .map(freePassRepository::saveAndFlush)
                    .map(freePassReadDtoMapper::map);
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
        emailService.sendSimpleEmail("khvatov64@yandex.ru", "ПРОБНОЕ ЗАНЯТИЕ ", message.toString());
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
                .map(freePassReadDtoMapper::map);
    }

    @Transactional
    public Boolean delete(Long id) {
        return freePassRepository.findById(id)
                .map(entity -> {
                    freePassRepository.delete(entity);
                    freePassRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}
