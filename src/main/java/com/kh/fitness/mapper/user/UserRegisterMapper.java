package com.kh.fitness.mapper.user;


import com.kh.fitness.dto.user.UserRegisterDto;
import com.kh.fitness.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserRegisterMapper {
    @Mapping(target = "id",ignore = true)
    @Mapping(target = "image",ignore = true)
    @Mapping(target = "roles",ignore = true)
    @Mapping(target = "authorities",ignore = true)
    User toEntity(UserRegisterDto s);
}