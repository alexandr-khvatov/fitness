package com.kh.fitness.service;

import com.kh.fitness.dto.account.AccountChangePasswordDto;
import com.kh.fitness.dto.account.AccountEditDto;
import com.kh.fitness.dto.user.UserCreateDto;
import com.kh.fitness.dto.user.UserCreatedDto;
import com.kh.fitness.dto.user.UserReadDto;
import com.kh.fitness.dto.user.UserRegisterDto;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<UserReadDto> findById(Long id);

    List<UserReadDto> findAll();

    UserReadDto create(@Valid UserCreateDto userDto);

    Optional<UserReadDto> updateAccount(@Valid AccountEditDto dto);

    Optional<UserReadDto> updateWithoutPassword(Long id,@Valid AccountEditDto dto);

    Optional<UserReadDto> update(Long id,@Valid AccountEditDto dto);

    boolean delete(Long id);

    UserCreatedDto register(@Valid UserRegisterDto userDto);

    void changePassword(@Valid AccountChangePasswordDto dto);

    List<UserReadDto> findAllWithRoleName(String name);

    Optional<UserReadDto> findByUsername(String username);

    boolean existsUserByPhone(String phone);

    boolean existsUserByEmail(String email);

}