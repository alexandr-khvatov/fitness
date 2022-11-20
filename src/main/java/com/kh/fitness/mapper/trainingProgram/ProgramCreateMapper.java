package com.kh.fitness.mapper.trainingProgram;

import com.kh.fitness.dto.trainingProgram.ProgramCreateDto;
import com.kh.fitness.entity.Gym;
import com.kh.fitness.entity.TrainingProgram;
import com.kh.fitness.mapper.Mapper;
import com.kh.fitness.repository.GymRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static java.util.function.Predicate.not;

@Component
@RequiredArgsConstructor
public class ProgramCreateMapper implements Mapper<ProgramCreateDto, TrainingProgram> {
    private final GymRepository gymRepository;

    @Override
    public TrainingProgram map(ProgramCreateDto f) {
        var program = new TrainingProgram();
        program.setName(f.getName());
        program.setOverview(f.getOverview());
        program.setDescription(f.getDescription());
        program.setGym(getGym(f.getGymId()));

        Optional.ofNullable(f.getImage())
                .filter(not(MultipartFile::isEmpty))
                .ifPresent(image -> program.setImage(image.getOriginalFilename()));

        return program;
    }

    public Gym getGym(Long gymId) {
        return Optional.ofNullable(gymId)
                .flatMap(gymRepository::findById)
                .orElseThrow(() -> new EntityNotFoundException("Entity Gym not found with id: " + gymId));
    }
}
