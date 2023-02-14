package com.kh.fitness.service;

import com.kh.fitness.dto.account.LoginDto;
import com.kh.fitness.dto.account.TokenDto;
import com.kh.fitness.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtEncoder;

import java.time.Instant;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TokenServiceImplTest {
    @Mock
    private JwtEncoder encoder;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private TokenServiceImpl tokenService;

    @Test
    void authenticationSuccess() {
        var stubAuthenticated = new UsernamePasswordAuthenticationToken("dummy", "dummy");
        doReturn(stubAuthenticated).when(authenticationManager).authenticate(any());

        String stubToken = "dummy MY SECRET TOKEN";
        Jwt stubJwt = new Jwt(stubToken, Instant.now(), Instant.MAX, Map.of("dummy", "headers"), Map.of("dummy", "claims"));
        doReturn(stubJwt).when(encoder).encode(any());

        var actualResult = tokenService.authentication(new LoginDto("dummy", "dummy"));


        TokenDto expectedResult = new TokenDto(stubToken);
        assertThat(actualResult).isEqualTo(expectedResult);
        verify(encoder).encode(any());
    }

    @Test
    void authenticationFail() {
        doThrow(BadCredentialsException.class).when(authenticationManager).authenticate(any());
        assertThrows(BadCredentialsException.class, () -> tokenService.authentication(new LoginDto("dummy", "dummy")));
        verifyNoInteractions(encoder);
    }

}