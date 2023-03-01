package com.kh.fitness.mapper.coach;

import com.kh.fitness.dto.coach.CoachReadDto;
import com.kh.fitness.entity.Coach;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CoachReadDtoMapper {
    @Mapping(target = "gymId", source = "gym.id")
    @Mapping(target = "image", expression = "java(s.getImage() != null ? \"http://localhost:8080/api/v1/coaches/\" + s.getId() + \"/avatar\" : null)")
    CoachReadDto toDto(Coach s);
}