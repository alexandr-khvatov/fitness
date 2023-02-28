package com.kh.fitness.model_builder;

import com.kh.fitness.entity.*;
import com.kh.fitness.entity.gym.Gym;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

public class TrainingTestBuilder {
    private static final Long id = 1L;
    private static final String name = "training_name";
    private static final LocalTime startTime = LocalTime.now();
    private static final LocalTime endTime = LocalTime.now();
    private static final DayOfWeek dayOfWeek = DayOfWeek.FRIDAY;
    private static final LocalDateTime date = LocalDateTime.now();
    private static final Integer takenSeats = 15;
    private static final Integer totalSeats = 20;
    private static final Coach coach = new Coach();
    private static final Gym gym = new Gym();
    private static final Room room = new Room();
    private static final SubTrainingProgram subTrainingProgram = new SubTrainingProgram();

    public static Training getTraining() {
        return Training.builder()
                .id(id)
                .name(name)
                .startTime(startTime)
                .endTime(endTime)
                .dayOfWeek(dayOfWeek)
                .date(date)
                .takenSeats(takenSeats)
                .totalSeats(totalSeats)
                .coach(coach)
                .room(room)
                .gym(gym)
                .subTrainingProgram(subTrainingProgram)
                .build();
    }

    public static Set<Training> getTrainings() {
        return Set.of(
                getTraining(),
                Training.builder()
                        .id(2L)
                        .name(name+1)
                        .startTime(startTime)
                        .endTime(endTime)
                        .dayOfWeek(dayOfWeek)
                        .date(date)
                        .takenSeats(takenSeats)
                        .totalSeats(totalSeats)
                        .coach(coach)
                        .room(room)
                        .gym(gym)
                        .subTrainingProgram(subTrainingProgram)
                        .build(),
                Training.builder()
                        .id(3L)
                        .name(name+2)
                        .startTime(startTime)
                        .endTime(endTime)
                        .dayOfWeek(dayOfWeek)
                        .date(date)
                        .takenSeats(takenSeats)
                        .totalSeats(totalSeats)
                        .coach(coach)
                        .room(room)
                        .gym(gym)
                        .subTrainingProgram(subTrainingProgram)
                        .build()
        );
    }
}