package com.kh.fitness.model_builder;

import com.kh.fitness.entity.gym.Gym;
import com.kh.fitness.entity.subscription.Period;
import com.kh.fitness.entity.subscription.Subscription;

import java.util.Set;

public class SubscriptionTestBuilder {
    private static final Long id = 1L;
    private static final String name = "subscription_name";
    private static final Integer price = 1000;
    private static final Period period =Period.ONE_MONTH;
    private static final Gym gym = new Gym();

    public static Subscription getSubscription() {
        return Subscription.builder()
                .id(id)
                .name(name)
                .price(price)
                .period(period)
                .gym(gym)
                .build();
    }

    public static Set<Subscription> getSubscriptions() {
        return Set.of(
                getSubscription(),
                Subscription.builder()
                        .id(2L)
                        .name("subscription_name2")
                        .period(period)
                        .gym(gym)
                        .gym(gym)
                        .build(),
                Subscription.builder()
                        .id(3L)
                        .name("subscription_name3")
                        .period(period)
                        .gym(gym)
                        .gym(gym)
                        .build());
    }
}