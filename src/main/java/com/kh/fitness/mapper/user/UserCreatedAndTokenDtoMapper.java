package com.kh.fitness.mapper.user;

import com.kh.fitness.dto.account.AccountCreatedAndTokenDto;
import com.kh.fitness.dto.user.UserCreatedDto;
import com.kh.fitness.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class UserCreatedAndTokenDtoMapper implements Mapper<UserCreatedDto, AccountCreatedAndTokenDto> {

    @Override
    public AccountCreatedAndTokenDto map(UserCreatedDto f) {
        return new AccountCreatedAndTokenDto(
                f.getId(),
                f.getFirstname(),
                f.getPatronymic(),
                f.getLastname(),
                f.getEmail(),
                f.getPhone(),
                f.getBirthDate(),
                f.getRoles(), null);
    }
}
