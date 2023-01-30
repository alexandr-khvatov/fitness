package com.kh.fitness.mapper.util.resolvers;

import com.kh.fitness.entity.TrainingProgram;
import org.springframework.data.jpa.repository.JpaRepository;

public class TrainingProgramMapperResolver extends EntityByJpaRepositoryMapperResolver<TrainingProgram, Long> {
    public TrainingProgramMapperResolver(JpaRepository<TrainingProgram, Long> repository) {
        super(repository);
    }
}
