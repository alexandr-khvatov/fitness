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

/*@Component
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
}*/
