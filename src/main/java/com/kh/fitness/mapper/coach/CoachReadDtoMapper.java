package com.kh.fitness.mapper.coach;

import com.kh.fitness.dto.coach.CoachReadDto;
import com.kh.fitness.entity.Coach;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CoachReadDtoMapper {
    @Mapping(target = "image", expression = "java(s.getImage() != null ? \"http://localhost:8080/api/v1/coaches/\" + s.getId() + \"/avatar\" : null)")
    CoachReadDto toDto(Coach s);
}

/*@Component
public class CoachReadDtoMapper implements Mapper<Coach, CoachReadDto> {
    @Override
    public CoachReadDto map(Coach f) {
        return CoachReadDto.builder()
                .id(f.getId())
                .firstname(f.getFirstname())
                .patronymic(f.getPatronymic())
                .lastname(f.getLastname())
                .birthDate(f.getBirthDate())
                .phone(f.getPhone())
                .email(f.getEmail())
                .specialization(f.getSpecialization())
                .image(f.getImage() != null ? "http://localhost:8080/api/v1/coaches/" + f.getId() + "/avatar" : null)
                .description(f.getDescription())
                .build();
    }
}*/
