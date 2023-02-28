package com.kh.fitness.mapper;

import com.kh.fitness.dto.role.RoleReadDto;
import com.kh.fitness.entity.user.Role;
import org.mapstruct.Mapper;

@Mapper
public interface RoleReadMapper {
    Role toRole(RoleReadDto f);
}
