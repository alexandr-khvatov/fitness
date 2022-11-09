package com.kh.fitness.mapper.account;

import com.kh.fitness.dto.account.AccountUpdatedAndTokenDto;
import com.kh.fitness.dto.user.UserReadDto;
import com.kh.fitness.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class AccountUpdatedAndTokenDtoMapper implements Mapper<UserReadDto, AccountUpdatedAndTokenDto> {

    @Override
    public AccountUpdatedAndTokenDto map(UserReadDto f) {
        return new AccountUpdatedAndTokenDto(
                f.getId(),
                f.getFirstname(),
                f.getPatronymic(),
                f.getLastname(),
                f.getEmail(),
                f.getPhone(),
                f.getBirthDate(),
                null);
    }
}
