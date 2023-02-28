package com.kh.fitness.model_builder;

import com.kh.fitness.entity.Coach;
import com.kh.fitness.entity.gym.Gym;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import static com.kh.fitness.model_builder.GymContactsTestBuilder.getGymContacts;
import static com.kh.fitness.model_builder.GymSocialMediaTestBuilder.getGymSocialMedia;

public class GymTestBuilder {
    private static final Long id = 1L;
    private static final String name = "gym_firstname";
    private static final LocalTime minStartTime = LocalTime.now();
    private static final LocalTime maxEndTime = LocalTime.now();
    private static final Set<Coach> coaches = new HashSet<>();

    public static Gym getGym() {
        return Gym.builder()
                .id(id)
                .name(name)
                .gymContacts(getGymContacts())
                .gymSocialMedia(getGymSocialMedia())
                .minStartTime(minStartTime)
                .coaches(coaches)
                .maxEndTime(maxEndTime)
                .build();
    }
}