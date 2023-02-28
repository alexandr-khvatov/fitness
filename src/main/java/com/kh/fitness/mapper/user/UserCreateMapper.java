package com.kh.fitness.mapper.user;


import com.kh.fitness.dto.user.UserCreateDto;
import com.kh.fitness.entity.user.User;
import com.kh.fitness.mapper.util.resolvers.RoleResolver;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@Mapper(uses = {RoleResolver.class})
public abstract class UserCreateMapper {
    @Autowired
    protected RoleResolver roleResolver;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "image", ignore = true)
    @Mapping(target = "roles", expression = "java(roleResolver.resolve(s.getRoles()))")
    public abstract User toEntity(UserCreateDto s);
}