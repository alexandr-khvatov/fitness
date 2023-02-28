package com.kh.fitness.mapper.gym;

import com.kh.fitness.dto.gym.GymOpeningHourInfoDto;
import com.kh.fitness.dto.gym.GymReadDto;
import com.kh.fitness.entity.gym.Gym;
import com.kh.fitness.entity.gym.GymOpeningHourInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface GymReadDtoMapper {
    @Mapping(target = "openingHours", expression = "java(openingHours(s.getOpeningHours()))")
    @Mapping(target = "address", source = "gymContacts.address")
    @Mapping(target = "phone", source = "gymContacts.phone")
    @Mapping(target = "email", source = "gymContacts.email")
    @Mapping(target = "vkLink", source = "gymSocialMedia.vkLink")
    @Mapping(target = "tgLink", source = "gymSocialMedia.tgLink")
    @Mapping(target = "instLink", source = "gymSocialMedia.instLink")
    @Mapping(target = "workingHoursOnWeekdays", ignore = true)
    @Mapping(target = "workingHoursOnWeekends", ignore = true)
    GymReadDto toDto(Gym s);

    default List<GymOpeningHourInfoDto> openingHours(List<GymOpeningHourInfo> openingHours) {
        return openingHours.stream().map(x -> GymOpeningHourInfoDto.builder()
                        .dayOfWeek(x.getDayOfWeek().getValue())
                        .startTime(x.getStartTime())
                        .endTime(x.getEndTime())
                        .isOpen(x.getIsOpen())
                        .build())
                .toList();
    }
}