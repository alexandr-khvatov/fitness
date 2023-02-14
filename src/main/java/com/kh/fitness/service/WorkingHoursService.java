package com.kh.fitness.service;

import com.kh.fitness.dto.gym.GymHours;
import com.kh.fitness.entity.GymOpeningHourInfo;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface WorkingHoursService {

    List<GymOpeningHourInfo> getOpeningHours(GymHours openingHours);

    Optional<LocalTime> getEarliestOpeningTime(GymHours openingHours);

    Optional<LocalTime> getLatestClosingTime(GymHours openingHours);
}
