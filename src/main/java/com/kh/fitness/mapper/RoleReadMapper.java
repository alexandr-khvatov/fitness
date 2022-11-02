package com.kh.fitness.mapper;

import com.kh.fitness.dto.RoleReadDto;
import com.kh.fitness.entity.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleReadMapper implements Mapper<RoleReadDto, Role> {


    @Override
    public Role map(RoleReadDto from) {
        Role role = new Role();
        return role;
    }

    private void copy(RoleReadDto object, Role role) {

    }
}
