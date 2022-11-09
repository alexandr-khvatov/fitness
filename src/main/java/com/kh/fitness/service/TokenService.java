package com.kh.fitness.service;

import com.kh.fitness.config.SecurityConfig;
import com.kh.fitness.dto.LoginDto;
import com.kh.fitness.dto.TokenDto;
import com.kh.fitness.exception.UserNotFoundException;
import com.kh.fitness.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Validated
public class TokenService {
    public static final String ISSUER = "self";
    private final JwtEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    public TokenDto authentication(@Valid LoginDto dto) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        dto.getUsername(),
                        dto.getPassword())
                );
        return generateToken(authentication);
    }

    // TODO нужно сделать так, чтобы старые токены удалялись(стали невалидными)
    public TokenDto updateToken(Long id) {
        var maybeUser = userRepository.findById(id);
        if (maybeUser.isPresent()) {
            var nowAuthorities = SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getAuthorities();
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    maybeUser.get().getPhone(),
                    maybeUser.get().getPassword(),
                    nowAuthorities
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return generateToken(authentication);
        } else {
            throw new UserNotFoundException(id);
        }
    }

    // TODO пофиксить баг к авторити добавляется префикс "ROLE_" несколько раз --->  "ROLE_ROLE_CUSTOMER"
    private TokenDto generateToken(Authentication authentication) {
        Instant now = Instant.now();
        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(ISSUER)
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.HOURS))
                .subject(authentication.getName())
                .claim(SecurityConfig.AUTHORITIES_CLAIM_NAME, scope)
                .build();
        return new TokenDto(this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue());
    }
}