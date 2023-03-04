package com.kh.fitness.mapper.account;

import com.kh.fitness.dto.account.AccountEditDto;
import com.kh.fitness.entity.gym.Gym;
import com.kh.fitness.entity.user.User;
import com.kh.fitness.mapper.util.resolvers.GymMapperResolver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class AccountEditMapperTest {

    @Mock
    private GymMapperResolver mapperResolver;

    @InjectMocks
    private AccountEditMapperImpl accountEditMapper;

    @Test
    void updateUser() {
        var dto = getAccountEditDto();
        doReturn(getGym()).when(mapperResolver).resolve(gymId);

        var actualResultUserUpdated = accountEditMapper.updateEntity(dto, new User());

        var expectedResult = getUser();
        assertAll(
                () -> assertEquals(expectedResult.getFirstname(), actualResultUserUpdated.getFirstname()),
                () -> assertEquals(expectedResult.getPatronymic(), actualResultUserUpdated.getPatronymic()),
                () -> assertEquals(expectedResult.getLastname(), actualResultUserUpdated.getLastname()),
                () -> assertEquals(expectedResult.getEmail(), actualResultUserUpdated.getEmail()),
                () -> assertEquals(expectedResult.getPhone(), actualResultUserUpdated.getPhone()),
                () -> assertEquals(expectedResult.getGym().getId(), actualResultUserUpdated.getGym().getId()),
                () -> assertEquals(expectedResult.getBirthDate(), actualResultUserUpdated.getBirthDate())
        );
    }

    private static final String firstName = "Firstname";
    private static final String patronymic = "patronymic";
    private static final String lastname = "lastname";
    private static final String email = "test@gmail.com";
    private static final String phone = "phone";
    private static final Long gymId = 1L;
    private static final LocalDate birthDate = LocalDate.now();

    private static AccountEditDto getAccountEditDto() {
        return AccountEditDto.builder()
                .firstname(firstName)
                .patronymic(patronymic)
                .lastname(lastname)
                .phone(phone)
                .email(email)
                .birthDate(birthDate)
                .gymId(gymId)
                .build();
    }

    private static User getUser() {
        return User.builder()
                .firstname(firstName)
                .patronymic(patronymic)
                .lastname(lastname)
                .phone(phone)
                .email(email)
                .birthDate(birthDate)
                .gym(getGym())
                .build();
    }

    private static Gym getGym() {
        return Gym.builder()
                .id(gymId)
                .build();
    }
}