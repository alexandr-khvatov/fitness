package com.kh.fitness.service;

import com.kh.fitness.dto.account.LoginDto;
import com.kh.fitness.dto.account.TokenDto;

import javax.validation.Valid;

public interface TokenService {
    TokenDto authentication(@Valid LoginDto dto);

    TokenDto updateToken(Long id);
}
