package com.kh.fitness.service.working_hours;

import com.kh.fitness.dto.gym.GymHours;
import com.kh.fitness.dto.gym.GymOpeningHourInfoDto;
import com.kh.fitness.entity.gym.GymOpeningHourInfo;
import com.kh.fitness.mapper.gym.GymOpeningHourInfoDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Service
@Primary
public class WorkingHoursServiceImpl implements WorkingHoursService {
    private final GymOpeningHourInfoDtoMapper openingHourInfoDtoMapper;

    @Override
    public List<GymOpeningHourInfo> getOpeningHours(GymHours openingHours) {
        return openingHours.getOpeningHours().stream()
                .map(openingHourInfoDtoMapper::toEntity)
                .collect(toList());
    }

    @Override
    public Optional<LocalTime> getEarliestOpeningTime(GymHours openingHours) {
        return openingHours.getOpeningHours().stream()
                .filter(GymOpeningHourInfoDto::getIsOpen)
                .map(GymOpeningHourInfoDto::getStartTime)
                .min(LocalTime::compareTo);
    }

    @Override
    public Optional<LocalTime> getLatestClosingTime(GymHours openingHours) {
        return openingHours.getOpeningHours().stream()
                .filter(GymOpeningHourInfoDto::getIsOpen)
                .map(GymOpeningHourInfoDto::getEndTime)
                .max(LocalTime::compareTo);
    }
}
