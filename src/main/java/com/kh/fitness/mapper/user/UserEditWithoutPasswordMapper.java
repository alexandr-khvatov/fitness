package com.kh.fitness.mapper.user;


import com.kh.fitness.dto.user.UserCreateDto;
import com.kh.fitness.entity.Role;
import com.kh.fitness.entity.User;
import com.kh.fitness.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Slf4j
@Mapper
public abstract class UserEditWithoutPasswordMapper {
    @Autowired
    private RoleRepository roleRepository;
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "image", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    @Mapping(target = "roles", expression = "java(rolesResolver(s.getRoles()))")
    public abstract User toEntity(UserCreateDto s);

    @InheritConfiguration
    public abstract User updateEntity(UserCreateDto s, @MappingTarget User t);

    protected Set<Role> rolesResolver(Set<Long> roles) {
        var maybeRoles = roleRepository.findAll().stream()
                .filter(role -> roles.contains(role.getId()))
                .collect(toSet());
        if (!maybeRoles.isEmpty()) {
            return maybeRoles;
        } else {
            log.error("Role set is empty or cannot be resolved,list of roles id that cannot be resolved: {}", roles);
            throw new IllegalStateException("Roles set that cannot be resolved");
        }
    }
}