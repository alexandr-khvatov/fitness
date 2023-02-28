package com.kh.fitness.service;

import com.kh.fitness.dto.gym.GymHours;
import com.kh.fitness.dto.gym.GymCreateEditDto;
import com.kh.fitness.dto.gym.GymOpeningHourInfoDto;
import com.kh.fitness.dto.gym.GymReadDto;
import com.kh.fitness.mapper.gym.GymCreateEditDtoMapper;
import com.kh.fitness.mapper.gym.GymReadDtoMapper;
import com.kh.fitness.repository.GymRepository;
import com.kh.fitness.repository.TrainingRepository;
import com.kh.fitness.service.working_hours.WorkingHoursService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.joining;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Validated
public class GymServiceImpl {
    private final GymRepository gymRepository;
    private final TrainingRepository trainingRepository;
    private final WorkingHoursService workingHoursService;

    private final GymCreateEditDtoMapper gymCreateEditDtoMapper;
    private final GymReadDtoMapper gymReadDtoMapper;

    private static final Map<Integer, String> DAY_OF_WEEK_RUSSIAN = Map.of(
            1, "Понедельник",
            2, "Вторник",
            3, "Среда",
            4, "Четверг",
            5, "Пятница",
            6, "Суббота",
            7, "Воскресение"
    );

    public Optional<GymReadDto> findById(Long id) {
        return gymRepository.findById(id)
                .map(gymReadDtoMapper::toDto);
    }

    public List<GymReadDto> findAll() {
        return gymRepository.findAll().stream()
                .map(gymReadDtoMapper::toDto)
                .toList();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @Transactional
    public GymReadDto create(@Valid GymCreateEditDto gym) {
        return Optional.of(gym)
                .map(gymCreateEditDtoMapper::toEntity)
                .map(gymRepository::save)
                .map(gymReadDtoMapper::toDto)
                .orElseThrow();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
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

    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @Transactional
    public Optional<GymReadDto> update(Long id, GymCreateEditDto updateGym) {
        return gymRepository.findById(id)
                .map(entity -> gymCreateEditDtoMapper.updateGym(updateGym, entity))
                .map(gymRepository::saveAndFlush)
                .map(gymReadDtoMapper::toDto);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @Transactional
    public Optional<GymReadDto> updateWorkingHours(Long id, @Valid GymHours openingHours) {
        checkDateRangeOrElseThrow(openingHours);
        checkWorkingDayOrElseThrow(openingHours);

        var maybeGym = gymRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity with id " + id + " not found"));

        maybeGym.setOpeningHours(workingHoursService.getOpeningHours(openingHours));
        workingHoursService.getEarliestOpeningTime(openingHours)
                .ifPresent(maybeGym::setMinStartTime);
        workingHoursService.getLatestClosingTime(openingHours)
                .ifPresent(maybeGym::setMaxEndTime);

        var persistedGym = gymRepository.saveAndFlush(maybeGym);
        return Optional.of(gymReadDtoMapper.toDto(persistedGym));
    }

    private void checkDateRangeOrElseThrow(GymHours openingHours) {
        for (var openHour : openingHours.getOpeningHours()) {
            if (openHour.getStartTime() == null || openHour.getEndTime() == null) {
                throw new IllegalArgumentException("Date not found");
            }
            if (!openHour.getStartTime().isBefore(openHour.getEndTime())) {
                throw new IllegalArgumentException("Invalid time range");
            }
        }
    }

    private void checkWorkingDayOrElseThrow(GymHours openingHours) {
        var nonWorkingDays = openingHours.getOpeningHours().stream()
                .filter(GymOpeningHourInfoDto::getIsOpen)
                .map(GymOpeningHourInfoDto::getDayOfWeek)
                .toList();

        var trainingsDayOfWeeks = trainingRepository.findAll().stream()
                .map(x -> x.getDayOfWeek().getValue())
                .filter(nonWorkingDays::contains)
                .toList();

        var notWorkDayError = trainingsDayOfWeeks.stream()
                .filter(nonWorkingDays::contains)
                .toList();

        if (!notWorkDayError.isEmpty()) {
            var nameDay = notWorkDayError.stream()
                    .map(DAY_OF_WEEK_RUSSIAN::get)
                    .collect(joining(", "));
            throw new IllegalArgumentException("В " + String.join(", ", nameDay) + " имеются занятия, не возможно установить не рабочий день");
        }
    }
}