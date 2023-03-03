package com.kh.fitness.service.user;

import com.kh.fitness.dto.account.AccountChangePasswordDto;
import com.kh.fitness.dto.user.*;
import com.kh.fitness.service.AvatarService;
import com.kh.fitness.validation.Phone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.Valid;
import java.util.Optional;

public interface UserService extends AvatarService {
    Optional<UserReadDto> findById(Long id);

    Page<UserReadDto> findAllByFilter(UserFilter filter, Pageable pageable);

    UserReadDto create(@Valid UserCreateDto userDto);

    Optional<UserReadDto> update(Long id, @Valid UserEditDto dto);

    boolean delete(Long id);

    UserCreatedDto register(@Valid UserRegisterDto userDto);

    void changePassword(@Valid AccountChangePasswordDto dto);

    Optional<UserReadDto> findByUsername(@Phone String username);

    boolean existsUserByPhone(String phone);

    boolean existsUserByEmail(String email);
}