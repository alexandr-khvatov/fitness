package com.kh.fitness.mapper.coach;

import com.kh.fitness.dto.coach.CoachCreateDto;
import com.kh.fitness.entity.Coach;
import com.kh.fitness.mapper.util.resolvers.GymMapperResolver;
import org.mapstruct.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static java.util.function.Predicate.not;

@Mapper(uses = {GymMapperResolver.class})
public abstract class CoachCreateMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "trainings", ignore = true)
    @Mapping(target = "image", ignore = true)
    @Mapping(target = "gym", source = "gymId")
    public abstract Coach toEntity(CoachCreateDto source);

    @AfterMapping
    protected void setImageName(CoachCreateDto source, @MappingTarget Coach coach){
        Optional.ofNullable(source.getImage())
                .filter(not(MultipartFile::isEmpty))
                .ifPresent(image -> coach.setImage(image.getOriginalFilename()));
    }
}