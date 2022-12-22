package com.kh.fitness.mapper;

import com.kh.fitness.dto.gym.GymOpeningHourInfoDto;
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
                .minStartTime(f.getMinStartTime())
                .maxEndTime(f.getMaxEndTime())
                .openingHours(f.getOpeningHours().stream().map(x -> GymOpeningHourInfoDto.builder()
                                .dayOfWeek(x.getDayOfWeek().getValue())
                                .startTime(x.getStartTime())
                                .endTime(x.getEndTime())
                                .isOpen(x.getIsOpen())
                                .build())
                        .toList())
                .build();
    }
}
