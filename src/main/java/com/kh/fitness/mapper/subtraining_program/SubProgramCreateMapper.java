package com.kh.fitness.mapper.subtraining_program;

import com.kh.fitness.dto.coach.CoachCreateDto;
import com.kh.fitness.dto.subtraining_program.SubProgramCreateDto;
import com.kh.fitness.entity.SubTrainingProgram;
import com.kh.fitness.mapper.util.resolvers.TrainingProgramMapperResolver;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static java.util.function.Predicate.not;

@Mapper(uses = {TrainingProgramMapperResolver.class})
public abstract class SubProgramCreateMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "image", ignore = true)
    @Mapping(target = "trainingProgram", source = "programId")
    public abstract SubTrainingProgram toEntity(SubProgramCreateDto s);

    @AfterMapping
    protected void setImageName(CoachCreateDto source, @MappingTarget SubTrainingProgram subTrainingProgram) {
        Optional.ofNullable(source.getImage())
                .filter(not(MultipartFile::isEmpty))
                .ifPresent(image -> subTrainingProgram.setImage(image.getOriginalFilename()));
    }
}