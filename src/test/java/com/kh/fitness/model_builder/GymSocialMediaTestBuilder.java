package com.kh.fitness.model_builder;

import com.kh.fitness.entity.gym.GymSocialMedia;

public class GymSocialMediaTestBuilder {

    private static final String vkLink = "gym_vkLink";
    private static final String tgLink = "gym_tgLink";
    private static final String instLink = "gym_instLink";

    public static GymSocialMedia getGymSocialMedia() {
        return GymSocialMedia.builder()
                .vkLink(vkLink)
                .tgLink(tgLink)
                .instLink(instLink)
                .build();
    }
}
