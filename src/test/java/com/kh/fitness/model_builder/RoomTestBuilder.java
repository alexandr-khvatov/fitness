package com.kh.fitness.model_builder;

import com.kh.fitness.entity.gym.Gym;
import com.kh.fitness.entity.Room;

import java.util.Set;

public class RoomTestBuilder {
    private static final Long id = 1L;
    private static final String name = "room_name";
    private static final Gym gym = new Gym();

    public static Room getRoom() {
        return Room.builder()
                .id(id)
                .name(name)
                .gym(gym)
                .build();
    }

    public static Set<Room> getRooms() {
        return Set.of(
                getRoom(),
                Room.builder()
                        .id(2L)
                        .name("room_name2")
                        .gym(gym)
                        .build(),
                Room.builder()
                        .id(3L)
                        .name("room_name3")
                        .gym(gym)
                        .build());
    }
}