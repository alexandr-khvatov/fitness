package com.kh.fitness.mapper.user;

import com.kh.fitness.dto.user.UserReadDto;
import com.kh.fitness.entity.user.Role;
import com.kh.fitness.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Mapper
public interface UserReadMapper {
    @Mapping(target = "gymId", source = "gym.id")
    @Mapping(target = "roles", expression = "java(roles(s.getRoles()))")
    @Mapping(target = "username", source = "phone")
    UserReadDto toDto(User s);

    default Set<Long> roles(Set<Role> roles) {
        return roles.stream()
                .map(Role::getId)
                .collect(toSet());
    }
}