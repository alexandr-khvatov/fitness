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
    public Gym map(GymCreateEditDto f) {
        return map(f, new Gym());
    }

    @Override
    public Gym map(GymCreateEditDto f, Gym t) {
        t.setName(f.getName());
        t.setGymContacts(GymContacts.builder()
                .address(f.getAddress())
                .phone(f.getPhone())
                .email(f.getEmail())
                .build());
        t.setGymSocialMedia(GymSocialMedia.builder()
                .vkLink(f.getVkLink())
                .tgLink(f.getTgLink())
                .instLink(f.getInstLink())
                .build());
        return t;
    }
}
