package com.kh.fitness.mapper.account;

import com.kh.fitness.dto.account.AccountEditDto;
import com.kh.fitness.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
//@RequiredArgsConstructor
class AccountEditMapperTest {
    @Autowired
    AccountEditMapper accountEditMapper;

    @Test
    void updateUser() {
        var dto = new AccountEditDto();
        var user = new User();
        dto.setFirstname("Firstname");
        dto.setEmail("test@gmail.com");
        var userUpdated = accountEditMapper.updateUser(dto, user);

        assertEquals(dto.getFirstname(), userUpdated.getFirstname());
        assertEquals(dto.getEmail(), userUpdated.getEmail());
    }
}