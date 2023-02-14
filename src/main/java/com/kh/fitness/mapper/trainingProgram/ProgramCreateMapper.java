package com.kh.fitness.mapper.trainingProgram;

import com.kh.fitness.dto.trainingProgram.ProgramCreateDto;
import com.kh.fitness.entity.TrainingProgram;
import com.kh.fitness.mapper.util.resolvers.GymMapperResolver;
import com.kh.fitness.mapper.util.resolvers.TrainingProgramMapperResolver;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static java.util.function.Predicate.not;

@Mapper(uses = {GymMapperResolver.class, TrainingProgramMapperResolver.class})
public abstract class ProgramCreateMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "image", ignore = true)
    @Mapping(target = "subTrainingPrograms", ignore = true)
    @Mapping(target = "gym", source = "gymId")
    public abstract TrainingProgram toEntity(ProgramCreateDto f);

    @AfterMapping
    protected void setImageName(ProgramCreateDto source, @MappingTarget TrainingProgram trainingProgram) {
        Optional.ofNullable(source.getImage())
                .filter(not(MultipartFile::isEmpty))
                .ifPresent(image -> trainingProgram.setImage(image.getOriginalFilename()));
    }
}