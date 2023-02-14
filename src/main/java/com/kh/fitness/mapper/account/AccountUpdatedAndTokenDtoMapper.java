package com.kh.fitness.mapper.account;

import com.kh.fitness.dto.account.AccountUpdatedAndTokenDto;
import com.kh.fitness.dto.user.UserReadDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface AccountUpdatedAndTokenDtoMapper {
    @Mapping(target = "token", ignore = true)
    AccountUpdatedAndTokenDto toDto(UserReadDto s);
}