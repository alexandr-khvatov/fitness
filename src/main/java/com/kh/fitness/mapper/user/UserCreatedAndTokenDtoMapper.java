package com.kh.fitness.mapper.user;

import com.kh.fitness.dto.account.AccountCreatedAndTokenDto;
import com.kh.fitness.dto.user.UserCreatedDto;
import com.kh.fitness.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class UserCreatedAndTokenDtoMapper implements Mapper<UserCreatedDto, AccountCreatedAndTokenDto> {

    @Override
    public AccountCreatedAndTokenDto map(UserCreatedDto from) {
        return new AccountCreatedAndTokenDto(
                from.getId(),
                from.getFirstname(),
                from.getPatronymic(),
                from.getLastname(),
                from.getEmail(),
                from.getPhone(),
                from.getBirthDate(),
                from.getRoles(), null);
    }
}
