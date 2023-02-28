package com.kh.fitness.service;

import com.kh.fitness.entity.user.User;
import com.kh.fitness.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChangePasswordServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private ChangePasswordService changePasswordService;

    @Test
    void changePassword_shouldChangeUserPwd_whenUserExist() {
        var user = getUser();
        var userOptional = Optional.of(user);
        var newPassword = "dummy";
        var savedUser = getUser();
        savedUser.setPassword(newPassword);

        doReturn(userOptional).when(userRepository).findByEmailIgnoreCase(user.getUsername());
        doReturn(newPassword).when(passwordEncoder).encode(newPassword);
        doReturn(savedUser).when(userRepository).saveAndFlush(user);
        changePasswordService.changePassword(newPassword, user.getUsername());

        assertThat(savedUser.getPassword()).isEqualTo(newPassword);
        verify(userRepository).findByEmailIgnoreCase(any());
        verify(userRepository).saveAndFlush(any());
        verify(passwordEncoder).encode(any());
    }

    @Test
    void changePassword_shouldThrowEntityNotFoundExc_whenUserNotExist() {
        doThrow(EntityNotFoundException.class).when(userRepository).findByEmailIgnoreCase(any());

        assertThrows(EntityNotFoundException.class, () -> changePasswordService.changePassword("dummy", "dummy"));
        verify(userRepository).findByEmailIgnoreCase(any());
        verify(userRepository, times(0)).saveAndFlush(any());
        verifyNoInteractions(passwordEncoder);
    }

    private User getUser() {
        var user = new User();
        user.setUsername("+78005553535");
        user.setPassword("user-secret-password");
        return user;
    }
}