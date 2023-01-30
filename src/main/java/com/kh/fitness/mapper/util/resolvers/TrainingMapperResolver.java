package com.kh.fitness.mapper.util.resolvers;

import com.kh.fitness.entity.Training;
import org.springframework.data.jpa.repository.JpaRepository;

public class TrainingMapperResolver extends EntityByJpaRepositoryMapperResolver<Training, Long> {
    public TrainingMapperResolver(JpaRepository<Training, Long> repository) {
        super(repository);
    }
}
