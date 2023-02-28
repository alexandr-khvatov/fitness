package com.kh.fitness.model_builder;

import com.kh.fitness.entity.gym.GymContacts;

public class GymContactsTestBuilder {

    private static final String address = "gym_address";
    private static final String phone = "gym_phone";
    private static final String email = "gym_email";

    public static GymContacts getGymContacts() {
        return GymContacts.builder()
                .address(address)
                .phone(phone)
                .email(email)
                .build();
    }
}

