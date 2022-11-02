package com.kh.fitness.api;

import com.kh.fitness.service.RoleService;
import com.kh.fitness.service.TokenService;
import com.kh.fitness.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.kh.fitness.api.util.PathUtils.API_V1;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_V1 + "/users")
@Validated
public class UserController {

    private final UserService userService;
    private final RoleService roleService;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
}