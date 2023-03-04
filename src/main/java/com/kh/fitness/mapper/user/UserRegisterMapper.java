package com.kh.fitness.mapper.user;


import com.kh.fitness.dto.user.UserRegisterDto;
import com.kh.fitness.entity.user.User;
import com.kh.fitness.mapper.util.resolvers.GymMapperResolver;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {GymMapperResolver.class})
public interface UserRegisterMapper {
    @Mapping(target = "id",ignore = true)
    @Mapping(target = "gym", source = "gymId")
    @Mapping(target = "image",ignore = true)
    @Mapping(target = "roles",ignore = true)
    User toEntity(UserRegisterDto s);
}