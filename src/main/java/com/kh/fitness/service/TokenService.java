package com.kh.fitness.service;

import com.kh.fitness.dto.LoginDto;
import com.kh.fitness.dto.TokenDto;

import javax.validation.Valid;

public interface TokenService {
    TokenDto authentication(@Valid LoginDto dto);

    // TODO нужно сделать так, чтобы старые токены удалялись(стали невалидными)
    TokenDto updateToken(Long id);
}
