package com.kh.fitness.mapper.user;

import com.kh.fitness.dto.user.UserReadDto;
import com.kh.fitness.entity.User;
import com.kh.fitness.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class UserReadMapper implements Mapper<User, UserReadDto> {

    @Override
    public UserReadDto map(User from) {
        return new UserReadDto(from.getId(),
                from.getFirstname(),
                from.getPatronymic(),
                from.getLastname(),
                from.getEmail(),
                from.getPhone(),
                from.getBirthDate());
    }
}
