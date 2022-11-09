package com.kh.fitness.mapper.user;

import com.kh.fitness.dto.user.UserReadDto;
import com.kh.fitness.entity.User;
import com.kh.fitness.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class UserReadMapper implements Mapper<User, UserReadDto> {

    @Override
    public UserReadDto map(User f) {
        return new UserReadDto(f.getId(),
                f.getFirstname(),
                f.getPatronymic(),
                f.getLastname(),
                f.getEmail(),
                f.getPhone(),
                f.getBirthDate());
    }
}
