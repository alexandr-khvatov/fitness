package com.kh.fitness.mapper.user;

import com.kh.fitness.dto.user.UserCreatedDto;
import com.kh.fitness.entity.User;
import com.kh.fitness.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class UserCreatedDtoMapper implements Mapper<User, UserCreatedDto> {

    @Override
    public UserCreatedDto map(User f) {
        return new UserCreatedDto(
                f.getId(),
                f.getFirstname(),
                f.getPatronymic(),
                f.getLastname(),
                f.getEmail(),
                f.getPhone(),
                f.getBirthDate(),
                f.getRoles());
    }
}
