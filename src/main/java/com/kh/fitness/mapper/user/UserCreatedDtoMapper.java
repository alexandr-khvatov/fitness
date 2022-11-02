package com.kh.fitness.mapper.user;

import com.kh.fitness.dto.user.UserCreatedDto;
import com.kh.fitness.entity.User;
import com.kh.fitness.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class UserCreatedDtoMapper implements Mapper<User, UserCreatedDto> {

    @Override
    public UserCreatedDto map(User from) {
        return new UserCreatedDto(
                from.getId(),
                from.getFirstname(),
                from.getPatronymic(),
                from.getLastname(),
                from.getEmail(),
                from.getPhone(),
                from.getBirthDate(),
                from.getRoles());
    }
}
