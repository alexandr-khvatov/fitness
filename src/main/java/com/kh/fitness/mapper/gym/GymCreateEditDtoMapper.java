package com.kh.fitness.mapper.gym;

import com.kh.fitness.dto.gym.GymCreateEditDto;
import com.kh.fitness.entity.gym.Gym;
import com.kh.fitness.entity.gym.GymContacts;
import com.kh.fitness.entity.gym.GymSocialMedia;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface GymCreateEditDtoMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "minStartTime", ignore = true)
    @Mapping(target = "maxEndTime", ignore = true)
    @Mapping(target = "openingHours", ignore = true)
    @Mapping(target = "coaches", ignore = true)
    @Mapping(target = "trainingPrograms", ignore = true)
    @Mapping(target = "subscriptions", ignore = true)
    @Mapping(target = "gymContacts", expression = "java(toGymContacts(s))")
    @Mapping(target = "gymSocialMedia", expression = "java(toGymSocialMedia(s))")
    Gym toEntity(GymCreateEditDto s);

    @InheritConfiguration
    Gym updateGym(GymCreateEditDto s, @MappingTarget Gym t);

    default GymContacts toGymContacts(GymCreateEditDto s) {
        return GymContacts.builder()
                .address(s.getAddress())
                .phone(s.getPhone())
                .email(s.getEmail())
                .build();
    }

    default GymSocialMedia toGymSocialMedia(GymCreateEditDto s) {
        return GymSocialMedia.builder()
                .vkLink(s.getVkLink())
                .tgLink(s.getTgLink())
                .instLink(s.getInstLink())
                .build();
    }
}