package com.kh.fitness.mapper.account;

import com.kh.fitness.dto.account.AccountEditDto;
import com.kh.fitness.entity.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AccountEditMapperImpl.class})
class AccountEditMapperTest {

    @Autowired
    AccountEditMapper accountEditMapper;

    @Test
    void updateUser() {
        var firstName = "Firstname";
        var patronymic = "patronymic";
        var lastname = "lastname";
        var email = "test@gmail.com";
        var phone = "phone";
        var birthDate = LocalDate.now();

        var dto = new AccountEditDto();
        dto.setFirstname(firstName);
        dto.setPatronymic(patronymic);
        dto.setLastname(lastname);
        dto.setPhone(phone);
        dto.setEmail(email);
        dto.setBirthDate(birthDate);

        var actualResultUserUpdated = accountEditMapper.updateEntity(dto, new User());

        var expectedResult = new User();
        expectedResult.setFirstname(firstName);
        expectedResult.setPatronymic(patronymic);
        expectedResult.setLastname(lastname);
        expectedResult.setPhone(phone);
        expectedResult.setEmail(email);
        expectedResult.setBirthDate(birthDate);

        assertAll(
                () -> assertEquals(expectedResult.getFirstname(), actualResultUserUpdated.getFirstname()),
                () -> assertEquals(expectedResult.getPatronymic(), actualResultUserUpdated.getPatronymic()),
                () -> assertEquals(expectedResult.getLastname(), actualResultUserUpdated.getLastname()),
                () -> assertEquals(expectedResult.getEmail(), actualResultUserUpdated.getEmail()),
                () -> assertEquals(expectedResult.getPhone(), actualResultUserUpdated.getPhone()),
                () -> assertEquals(expectedResult.getBirthDate(), actualResultUserUpdated.getBirthDate())
        );
    }
}