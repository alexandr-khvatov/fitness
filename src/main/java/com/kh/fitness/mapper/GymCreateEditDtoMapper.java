package com.kh.fitness.mapper;

import com.kh.fitness.dto.gym.GymCreateEditDto;
import com.kh.fitness.entity.Gym;
import com.kh.fitness.entity.GymContacts;
import com.kh.fitness.entity.GymSocialMedia;
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

//@Component
//public class GymCreateEditDtoMapper implements Mapper<GymCreateEditDto, Gym> {
//    @Override
//    public Gym map(GymCreateEditDto f) {
//        return map(f, new Gym());
//    }
//
//    @Override
//    public Gym map(GymCreateEditDto f, Gym t) {
//        t.setName(f.getName());
//        t.setGymContacts(GymContacts.builder()
//                .address(f.getAddress())
//                .phone(f.getPhone())
//                .email(f.getEmail())
//                .build());
//        t.setGymSocialMedia(GymSocialMedia.builder()
//                .vkLink(f.getVkLink())
//                .tgLink(f.getTgLink())
//                .instLink(f.getInstLink())
//                .build());
//        return t;
//    }
//}
