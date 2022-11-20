package com.kh.fitness.mapper.subTrainingProgram;

import com.kh.fitness.dto.subTrainingProgram.SubProgramCreateDto;
import com.kh.fitness.entity.SubTrainingProgram;
import com.kh.fitness.entity.TrainingProgram;
import com.kh.fitness.mapper.Mapper;
import com.kh.fitness.repository.ProgramRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static java.util.function.Predicate.not;

@Component
@RequiredArgsConstructor
public class SubProgramCreateMapper implements Mapper<SubProgramCreateDto, SubTrainingProgram> {
    private final ProgramRepository programRepository;

    @Override
    public SubTrainingProgram map(SubProgramCreateDto f) {
        var program = new SubTrainingProgram();
        copy(f, program);
        return program;
    }

    private void copy(SubProgramCreateDto f, SubTrainingProgram t) {
        t.setName(f.getName());
        t.setOverview(f.getOverview());
        t.setDescription(f.getDescription());
        t.setTrainingProgram(getTrainingProgram(f.getProgramId()));
        Optional.ofNullable(f.getImage())
                .filter(not(MultipartFile::isEmpty))
                .ifPresent(image -> t.setImage(image.getOriginalFilename()));
    }

    private TrainingProgram getTrainingProgram(Long programId) {
        return Optional.ofNullable(programId)
                .flatMap(programRepository::findById)
                .orElseThrow(() -> new EntityNotFoundException("Entity TrainingProgram not found with id: " + programId));
    }
}
