package com.kh.fitness.model_builder;

import com.kh.fitness.entity.gym.Gym;
import com.kh.fitness.entity.SubTrainingProgram;
import com.kh.fitness.entity.TrainingProgram;

import java.util.HashSet;
import java.util.Set;

public class TrainingProgramTestBuilder {
    private static final Long id = 1L;
    private static final String name = "training-program_name";
    private static final String overview = "training-program_name";
    private static final String image = "training-program_name";
    private static final String description = "training-program_name";
    private static final Set<SubTrainingProgram> subTrainingProgramPrograms = new HashSet<>();
    private static final Gym gym = new Gym();

    public static TrainingProgram getTrainingProgram() {
        return TrainingProgram.builder()
                .id(id)
                .name(name)
                .overview(overview)
                .image(image)
                .description(description)
                .subTrainingPrograms(subTrainingProgramPrograms)
                .gym(gym)
                .build();
    }

    public static Set<TrainingProgram> getTrainingPrograms() {
        return Set.of(
                getTrainingProgram(),
                TrainingProgram.builder()
                        .id(2L)
                        .name("training-program_name2")
                        .overview(overview)
                        .image(image)
                        .description(description)
                        .subTrainingPrograms(subTrainingProgramPrograms)
                        .gym(gym)
                        .build(),
                TrainingProgram.builder()
                        .id(3L)
                        .name("training-program_name3")
                        .overview(overview)
                        .image(image)
                        .description(description)
                        .subTrainingPrograms(subTrainingProgramPrograms)
                        .gym(gym)
                        .build());
    }
}