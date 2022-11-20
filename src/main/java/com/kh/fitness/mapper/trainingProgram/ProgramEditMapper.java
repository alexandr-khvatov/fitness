package com.kh.fitness.mapper.trainingProgram;

import com.kh.fitness.dto.trainingProgram.ProgramEditDto;
import com.kh.fitness.entity.Gym;
import com.kh.fitness.entity.TrainingProgram;
import com.kh.fitness.mapper.Mapper;
import com.kh.fitness.repository.GymRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProgramEditMapper implements Mapper<ProgramEditDto, TrainingProgram> {
    private final GymRepository gymRepository;

    @Override
    public TrainingProgram map(ProgramEditDto f) {
        var program = new TrainingProgram();
        copy(f, program);
        return program;
    }

    public TrainingProgram map(ProgramEditDto f, TrainingProgram t) {
        copy(f, t);
        return t;
    }

    public Gym getGym(Long gymId) {
        return Optional.ofNullable(gymId)
                .flatMap(gymRepository::findById)
                .orElseThrow(() -> new EntityNotFoundException("Entity Gym not found with id: " + gymId));
    }

    private void copy(ProgramEditDto f, TrainingProgram t) {
        t.setName(f.getName());
        t.setOverview(f.getOverview());
        t.setDescription(f.getDescription());
        t.setGym(getGym(f.getGymId()));
    }
}
