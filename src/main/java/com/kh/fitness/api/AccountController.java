package com.kh.fitness.api;

import com.kh.fitness.dto.LoginDto;
import com.kh.fitness.dto.account.AccountChangePasswordDto;
import com.kh.fitness.dto.account.AccountCreatedAndTokenDto;
import com.kh.fitness.dto.user.UserCreatedDto;
import com.kh.fitness.dto.user.UserRegisterDto;
import com.kh.fitness.mapper.user.UserCreatedAndTokenDtoMapper;
import com.kh.fitness.service.TokenService;
import com.kh.fitness.service.UserService;
import com.kh.fitness.api.util.PathUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(PathUtils.API_V1 + "/accounts")
@Validated
public class AccountController {

    private final UserService userService;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final UserCreatedAndTokenDtoMapper userCreatedAndTokenDtoMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountCreatedAndTokenDto register(@RequestBody UserRegisterDto dto) {
        var createdUser = userService.register(dto);
        var token = tokenService.authentication(new LoginDto(dto.getPhone(), dto.getPassword()));
        return setTokenForUser(createdUser, token.token());
    }

    @PostMapping("/change_password")
    public void changePassword(@RequestBody AccountChangePasswordDto dto,
                               @CurrentSecurityContext(expression = "authentication?.name") String username) {
        userService.changePassword(dto);
    }

    private AccountCreatedAndTokenDto setTokenForUser(UserCreatedDto user, String token) {
        var userWithoutToken = userCreatedAndTokenDtoMapper.map(user);
        userWithoutToken.setToken(token);
        return userWithoutToken;
    }

    /* *//**
     * Method checks if a user not exists in the database with such email
     * <p>
     * Response status:
     * 200 - user with this @param email not exists,
     * 409 - user with this @param email exists,
     * 400 - email validation error
     *
     * @param email string
     *//*
    @RequestMapping(path = "/validate_email/{email}", method = RequestMethod.HEAD)
    public void userNotExistsByEmail(@PathVariable @Email String email) {
        if (userService.existsUserByEmail(email))
            throw new ResponseStatusException(CONFLICT);
    }

    *//**
     * Method checks if a user not exists in the database with such a phone
     * <p>
     * Response status:
     * 200 - user with this @param phone not exists,
     * 409 - user with this @param phone exists,
     * 400 - phone validation error
     *
     * @param phone string
     *//*
    @RequestMapping(path = "/validate_phone/{phone}", method = RequestMethod.HEAD)
    public void userNotExistsByPhone(@PathVariable @Phone String phone) {
        if (userService.existsUserByPhone(phone)) {
            throw new ResponseStatusException(CONFLICT);
        }
    }*/
}