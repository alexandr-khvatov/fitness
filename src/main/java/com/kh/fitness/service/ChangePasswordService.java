package com.kh.fitness.service;

import com.kh.fitness.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChangePasswordService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void changePassword(String newRawPassword, String username) {
        var maybeUser = userRepository.findByEmailIgnoreCase(username)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Entity User with username %s not found", username)));
        maybeUser.setPassword(passwordEncoder.encode(newRawPassword));
        userRepository.saveAndFlush(maybeUser);
    }
}