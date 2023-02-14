package com.kh.fitness.mapper.user;

import com.kh.fitness.dto.account.AccountCreatedAndTokenDto;
import com.kh.fitness.dto.user.UserCreatedDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserCreatedAndTokenDtoMapper {

    @Mapping(target = "token", ignore = true)
    AccountCreatedAndTokenDto toDto(UserCreatedDto s);
}