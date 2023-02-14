package com.kh.fitness.mapper.util.resolvers;

import com.kh.fitness.entity.SubTrainingProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public class SubTrainingProgramMapperResolver extends EntityByJpaRepositoryMapperResolver<SubTrainingProgram, Long> {
    public SubTrainingProgramMapperResolver(JpaRepository<SubTrainingProgram, Long> repository) {
        super(repository);
    }
}
