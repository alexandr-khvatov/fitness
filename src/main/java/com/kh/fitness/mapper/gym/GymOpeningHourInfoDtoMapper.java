package com.kh.fitness.mapper.gym;

import com.kh.fitness.dto.gym.GymOpeningHourInfoDto;
import com.kh.fitness.entity.gym.GymOpeningHourInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.DayOfWeek;

@Mapper(uses = {DayOfWeek.class})
public interface GymOpeningHourInfoDtoMapper {
    @Mapping(target = "dayOfWeek", expression = "java(getDayOfWeek(source.getDayOfWeek()))")
    @Mapping(target = "isOpen", expression = "java(!source.getIsOpen())")
    GymOpeningHourInfo toEntity(GymOpeningHourInfoDto source);

    default DayOfWeek getDayOfWeek(Integer day) {
        return DayOfWeek.of(day);
    }
}
