package com.kh.fitness.mapper.coach;

import com.kh.fitness.dto.coach.CoachReadDto;
import com.kh.fitness.entity.Coach;
import com.kh.fitness.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
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
                .image(f.getImage())
                .description(f.getDescription())
                .build();
    }
}
