package com.kh.fitness.api;

import com.kh.fitness.api.util.PathUtils;
import com.kh.fitness.dto.LoginDto;
import com.kh.fitness.dto.account.AccountChangePasswordDto;
import com.kh.fitness.dto.account.AccountCreatedAndTokenDto;
import com.kh.fitness.dto.account.AccountEditDto;
import com.kh.fitness.dto.account.AccountUpdatedAndTokenDto;
import com.kh.fitness.dto.user.UserCreatedDto;
import com.kh.fitness.dto.user.UserRegisterDto;
import com.kh.fitness.exception.UserNotFoundException;
import com.kh.fitness.mapper.account.AccountUpdatedAndTokenDtoMapper;
import com.kh.fitness.mapper.user.UserCreatedAndTokenDtoMapper;
import com.kh.fitness.service.TokenService;
import com.kh.fitness.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(PathUtils.API_V1 + "/accounts")
@Validated
public class AccountController {
    private final UserService userService;
    private final TokenService tokenService;

    private final UserCreatedAndTokenDtoMapper userCreatedAndTokenDtoMapper;
    private final AccountUpdatedAndTokenDtoMapper accountUpdatedAndTokenDtoMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountCreatedAndTokenDto register(@RequestBody UserRegisterDto dto) {
        var createdUser = userService.register(dto);
        var token = tokenService.authentication(new LoginDto(dto.getPhone(), dto.getPassword()));
        return setTokenForUser(createdUser, token.token());
    }

    @PutMapping
    public AccountUpdatedAndTokenDto update(@RequestBody AccountEditDto dto, Authentication authentication) {
        return userService.updateAccount(dto)
                .map(updatedUser -> {
                    var userAndAccessToken = accountUpdatedAndTokenDtoMapper.map(updatedUser);
                    // create new token if change username(phone) and return
                    if (!Objects.equals(authentication.getName(), updatedUser.getPhone())) {
                        var accessToken = tokenService.updateToken(updatedUser.getId());
                        userAndAccessToken.setToken(accessToken.token());
                    }
                    return userAndAccessToken;
                })
                .orElseThrow(() -> new UserNotFoundException(authentication.getName()));
    }

    @PutMapping("/change_password")
    public void changePassword(@RequestBody AccountChangePasswordDto dto) {
        userService.changePassword(dto);
    }

    private AccountCreatedAndTokenDto setTokenForUser(UserCreatedDto user, String token) {
        var userWithoutToken = userCreatedAndTokenDtoMapper.map(user);
        userWithoutToken.setToken(token);
        return userWithoutToken;
    }
}