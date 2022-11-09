package com.kh.fitness.mapper.account;

import com.kh.fitness.dto.account.AccountEditDto;
import com.kh.fitness.entity.User;
import com.kh.fitness.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountEditDtoMapper implements Mapper<AccountEditDto, User> {

    @Override
    public User map(AccountEditDto f, User t) {
        copy(f, t);
        return t;
    }

    @Override
    public User map(AccountEditDto f) {
        User user = new User();
        copy(f, user);
        return user;
    }

    private void copy(AccountEditDto o, User user) {
        user.setFirstname(o.getFirstname());
        user.setPatronymic(o.getPatronymic());
        user.setLastname(o.getLastname());
        user.setEmail(o.getEmail());
        user.setPhone(o.getPhone());
        user.setBirthDate(o.getBirthDate());
    }
}