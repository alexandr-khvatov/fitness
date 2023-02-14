package com.kh.fitness.mapper;

import com.kh.fitness.dto.role.RoleReadDto;
import com.kh.fitness.entity.Role;
import org.mapstruct.Mapper;

@Mapper
public interface RoleReadMapper {
    public Role toRole(RoleReadDto f);
}
