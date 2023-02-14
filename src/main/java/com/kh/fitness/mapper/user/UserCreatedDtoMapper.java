package com.kh.fitness.mapper.user;


import com.kh.fitness.dto.user.UserCreatedDto;
import com.kh.fitness.entity.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserCreatedDtoMapper {
    UserCreatedDto toDto(User s);
}