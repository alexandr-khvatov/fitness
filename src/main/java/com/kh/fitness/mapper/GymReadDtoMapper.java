package com.kh.fitness.mapper;

import com.kh.fitness.dto.gym.GymReadDto;
import com.kh.fitness.entity.Gym;
import org.springframework.stereotype.Component;

@Component
public class GymReadDtoMapper implements Mapper<Gym, GymReadDto> {
    @Override
    public GymReadDto map(Gym from) {
        return GymReadDto.builder()
                .id(from.getId())
                .name(from.getName())
                .address(from.getGymContacts().getAddress())
                .phone(from.getGymContacts().getPhone())
                .email(from.getGymContacts().getEmail())
                .vkLink(from.getGymSocialMedia().getVkLink())
                .tgLink(from.getGymSocialMedia().getTgLink())
                .instLink(from.getGymSocialMedia().getInstLink())
                .workingHoursOnWeekdays(from.getWorkingHours().getWorkingHoursOnWeekdays())
                .workingHoursOnWeekends(from.getWorkingHours().getWorkingHoursOnWeekends())
                .build();
    }
}
