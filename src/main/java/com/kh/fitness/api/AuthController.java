package com.kh.fitness.api;

import com.kh.fitness.dto.account.LoginDto;
import com.kh.fitness.dto.account.TokenDto;
import com.kh.fitness.service.token.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.kh.fitness.api.util.PathUtils.API_V1;

@RestController
@RequestMapping(API_V1 + "/auth")
@RequiredArgsConstructor
public class AuthController {

    private final TokenService tokenService;

    @PostMapping("/tokens")
    public TokenDto login(@RequestBody LoginDto dto) {
        return tokenService.authentication(dto);
    }
}