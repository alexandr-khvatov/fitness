package com.kh.fitness.mapper.util.resolvers;

import com.kh.fitness.entity.SubTrainingProgram;
import com.kh.fitness.entity.Training;
import org.springframework.data.jpa.repository.JpaRepository;

public class SubTrainingProgramMapperResolver extends EntityByJpaRepositoryMapperResolver<SubTrainingProgram, Long> {
    public SubTrainingProgramMapperResolver(JpaRepository<SubTrainingProgram, Long> repository) {
        super(repository);
    }
}
