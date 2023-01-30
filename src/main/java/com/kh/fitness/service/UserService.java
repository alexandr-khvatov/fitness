package com.kh.fitness.service;

import com.kh.fitness.dto.account.AccountChangePasswordDto;
import com.kh.fitness.dto.account.AccountEditDto;
import com.kh.fitness.dto.user.UserCreateDto;
import com.kh.fitness.dto.user.UserCreatedDto;
import com.kh.fitness.dto.user.UserReadDto;
import com.kh.fitness.dto.user.UserRegisterDto;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<UserReadDto> findById(Long id);

    List<UserReadDto> findAll();

    UserReadDto create(UserCreateDto userDto);

    Optional<UserReadDto> updateAccount(AccountEditDto dto);

    Optional<UserReadDto> updateWithoutPassword(Long id, AccountEditDto dto);

    Optional<UserReadDto> update(Long id, AccountEditDto dto);

    boolean delete(Long id);

    UserCreatedDto register(final UserRegisterDto userDto);

    void changePassword(final AccountChangePasswordDto dto);

    List<UserReadDto> findAllWithRoleName(String name);

    Optional<UserReadDto> findByUsername(String username);

    boolean existsUserByPhone(String phone);

    boolean existsUserByEmail(String email);


}
