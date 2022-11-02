package com.kh.fitness.service;

import com.kh.fitness.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChangePasswordService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void changePassword(String newRawPassword, String username) {
        userRepository.findByEmailIgnoreCase(username).ifPresent(x -> {
            x.setPassword(passwordEncoder.encode(newRawPassword));
            userRepository.saveAndFlush(x);
        });
    }
}