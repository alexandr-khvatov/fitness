package com.kh.fitness.mapper;

import com.kh.fitness.dto.gym.GymCreateEditDto;
import com.kh.fitness.entity.Gym;
import com.kh.fitness.entity.GymContacts;
import com.kh.fitness.entity.GymSocialMedia;
import com.kh.fitness.entity.WorkingHours;
import org.springframework.stereotype.Component;

@Component
public class GymCreateEditDtoMapper implements Mapper<GymCreateEditDto, Gym> {
    @Override
    public Gym map(GymCreateEditDto from) {
        return map(from, new Gym());
    }

    @Override
    public Gym map(GymCreateEditDto from, Gym to) {
        to.setName(from.getName());
        to.setGymContacts(GymContacts.builder()
                .address(from.getAddress())
                .phone(from.getPhone())
                .email(from.getEmail())
                .build());
        to.setWorkingHours(WorkingHours.builder()
                .workingHoursOnWeekdays(from.getWorkingHoursOnWeekdays())
                .workingHoursOnWeekends(from.getWorkingHoursOnWeekends())
                .build());
        to.setGymSocialMedia(GymSocialMedia.builder()
                .vkLink(from.getVkLink())
                .tgLink(from.getTgLink())
                .instLink(from.getInstLink())
                .build());
        return to;
    }
}
