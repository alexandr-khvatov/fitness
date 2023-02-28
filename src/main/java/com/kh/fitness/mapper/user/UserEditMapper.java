package com.kh.fitness.mapper.user;


import com.kh.fitness.dto.user.UserEditDto;
import com.kh.fitness.entity.user.User;
import com.kh.fitness.mapper.util.resolvers.RoleResolver;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@Mapper
public abstract class UserEditMapper {

    @Autowired
    protected RoleResolver roleResolver;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "image", ignore = true)
    @Mapping(target = "roles", expression = "java(roleResolver.resolve(s.getRoles()))")
    public abstract User toEntity(UserEditDto s);

    @Mapping(target = "username", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "image", ignore = true)
    @Mapping(target = "id", ignore = true)
    @InheritConfiguration
    public abstract User updateEntity(UserEditDto s, @MappingTarget User t);
}