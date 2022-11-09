package com.kh.fitness.mapper;

import com.kh.fitness.dto.gym.GymReadDto;
import com.kh.fitness.entity.Gym;
import org.springframework.stereotype.Component;

@Component
public class GymReadDtoMapper implements Mapper<Gym, GymReadDto> {
    @Override
    public GymReadDto map(Gym f) {
        return GymReadDto.builder()
                .id(f.getId())
                .name(f.getName())
                .address(f.getGymContacts().getAddress())
                .phone(f.getGymContacts().getPhone())
                .email(f.getGymContacts().getEmail())
                .vkLink(f.getGymSocialMedia().getVkLink())
                .tgLink(f.getGymSocialMedia().getTgLink())
                .instLink(f.getGymSocialMedia().getInstLink())
                .workingHoursOnWeekdays(f.getWorkingHours().getWorkingHoursOnWeekdays())
                .workingHoursOnWeekends(f.getWorkingHours().getWorkingHoursOnWeekends())
                .build();
    }
}
