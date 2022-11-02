package com.kh.fitness.mapper.user;

import com.kh.fitness.dto.user.UserRegisterDto;
import com.kh.fitness.entity.User;
import com.kh.fitness.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRegisterMapper implements Mapper<UserRegisterDto, User> {
    @Override
    public User map(UserRegisterDto object) {
        User user = new User();
        copy(object, user);
        return user;
    }

    private void copy(UserRegisterDto o, User user) {
        user.setEmail(o.getEmail());
        user.setPhone(o.getPhone());
        user.setFirstname(o.getFirstname());
        user.setPatronymic(o.getPatronymic());
        user.setLastname(o.getLastname());
        user.setBirthDate(o.getBirthDate());
        user.setPassword(o.getPassword());
    }
}