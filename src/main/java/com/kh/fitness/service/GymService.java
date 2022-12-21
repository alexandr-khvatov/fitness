package com.kh.fitness.service;

import com.kh.fitness.dto.GymHours;
import com.kh.fitness.dto.gym.GymCreateEditDto;
import com.kh.fitness.dto.gym.GymOpeningHourInfoDto;
import com.kh.fitness.dto.gym.GymReadDto;
import com.kh.fitness.entity.Gym;
import com.kh.fitness.entity.GymOpeningHourInfo;
import com.kh.fitness.exception.IncorrectRange;
import com.kh.fitness.mapper.GymCreateEditDtoMapper;
import com.kh.fitness.mapper.GymReadDtoMapper;
import com.kh.fitness.repository.GymRepository;
import com.kh.fitness.repository.TrainingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Validated
public class GymService {
    private final GymRepository gymRepository;
    private final TrainingRepository trainingRepository;

    private final GymCreateEditDtoMapper gymCreateEditDtoMapper;
    private final GymReadDtoMapper gymReadDtoMapper;

    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    public GymReadDto create(@Valid GymCreateEditDto gym) {
        return Optional.of(gym)
                .map(gymCreateEditDtoMapper::map)
                .map(gymRepository::save)
                .map(gymReadDtoMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<GymReadDto> update(Long id, GymCreateEditDto updateGym) {
        return gymRepository.findById(id)
                .map(entity -> gymCreateEditDtoMapper.map(updateGym, entity))
                .map(gymRepository::saveAndFlush)
                .map(gymReadDtoMapper::map);
    }

    @Transactional
    public Optional<GymReadDto> updateOpeningHours(Long id, @Valid GymHours openingHours) {
        for (var openHour : openingHours.getOpeningHours()) {
            if (openHour.getStartTime() == null || openHour.getEndTime() == null) {
                throw new IncorrectRange("Не указана дата");
            }
            if (!openHour.getStartTime().isBefore(openHour.getEndTime())) {
                throw new IncorrectRange("Неверный диапазон времени");
            }
        }

        var notOpenDayOfWeeks = openingHours.getOpeningHours().stream()
                .filter(x -> x.getIsOpen())
                .map(GymOpeningHourInfoDto::getDayOfWeek)
                .toList();
        var trainingsDayOfWeeks = trainingRepository.findAll().stream()
                .map(x -> x.getDayOfWeek().getValue())
                .filter(notOpenDayOfWeeks::contains)
                .toList();
        var message = new StringBuilder();
        var utilDayOfWeekNameRussian = Map.of(
                1, "Понедельник",
                2, "Вторник",
                3, "Среда",
                4, "Четверг",
                5, "Пятница",
                6, "Суббота",
                7, "Воскресение"
        );

        var notWorkDayErorr = trainingsDayOfWeeks.stream().filter(notOpenDayOfWeeks::contains).toList();

        if (!notWorkDayErorr.isEmpty()) {
            var nameDay = notWorkDayErorr.stream()
                    .map(utilDayOfWeekNameRussian::get)
                    .collect(joining(", "));
            message.append(String.join(", ", nameDay));
            throw new IncorrectRange("В " + message + " имеются занятия, не возможно установить не рабочий день");
        }

        var maybeGym = gymRepository.findById(id).orElseThrow();

        maybeGym.setOpeningHours(openingHours.getOpeningHours().stream()
                .map(x -> GymOpeningHourInfo.of(
                        DayOfWeek.of(x.getDayOfWeek()),
                        x.getStartTime(),
                        x.getEndTime(),
                        !x.getIsOpen()
                ))
                .collect(toList()));

        var minStartTime = openingHours.getOpeningHours().stream()
                .filter(x -> !x.getIsOpen())
                .map(GymOpeningHourInfoDto::getStartTime)
                .min(LocalTime::compareTo);
        var maxEndTime = openingHours.getOpeningHours().stream()
                .filter(x -> !x.getIsOpen())
                .map(GymOpeningHourInfoDto::getEndTime)
                .max(LocalTime::compareTo);
        minStartTime.ifPresent(maybeGym::setMinStartTime);
        maxEndTime.ifPresent(maybeGym::setMaxEndTime);

        var savedGym = gymRepository.saveAndFlush(maybeGym);
        return Optional.of(gymReadDtoMapper.map(savedGym));
    }

    @Transactional
    public boolean delete(Long id) {
        return gymRepository.findById(id)
                .map(entity -> {
                    gymRepository.delete(entity);
                    gymRepository.flush();
                    return true;
                })
                .orElse(false);
    }

    public Optional<GymReadDto> findById(Long id) {
        return gymRepository.findById(id)
                .map(gymReadDtoMapper::map);
    }

    public List<Gym> findAll() {
        return gymRepository.findAll();
    }

    public Optional<GymReadDto> findByName(String name) {
        return gymRepository.findByName(name)
                .map(gymReadDtoMapper::map);
    }
}