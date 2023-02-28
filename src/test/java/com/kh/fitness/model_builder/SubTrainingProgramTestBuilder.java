package com.kh.fitness.model_builder;

import com.kh.fitness.entity.gym.Gym;
import com.kh.fitness.entity.SubTrainingProgram;
import com.kh.fitness.entity.TrainingProgram;

import java.util.Set;

public class SubTrainingProgramTestBuilder {
    private static final Long id = 1L;
    private static final String name = "training-program_name";
    private static final String overview = "training-program_name";
    private static final String image = "training-program_name";
    private static final String description = "training-program_name";
    private static final TrainingProgram trainingProgram = new TrainingProgram();
    private static final Gym gym = new Gym();

    public static SubTrainingProgram getSubTrainingProgram() {
        return SubTrainingProgram.builder()
                .id(id)
                .name(name)
                .overview(overview)
                .image(image)
                .description(description)
                .trainingProgram(trainingProgram)
                .build();
    }

    public static Set<SubTrainingProgram> getSubTrainingPrograms() {
        return Set.of(
                getSubTrainingProgram(),
                SubTrainingProgram.builder()
                        .id(2L)
                        .name("training-program_name2")
                        .overview(overview)
                        .image(image)
                        .description(description)
                        .trainingProgram(trainingProgram)
                        .build(),
                SubTrainingProgram.builder()
                        .id(3L)
                        .name("training-program_name3")
                        .overview(overview)
                        .image(image)
                        .description(description)
                        .trainingProgram(trainingProgram)
                        .build());
    }
}