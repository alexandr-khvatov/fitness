package com.kh.fitness.exception;

public class UserNotFoundException extends RuntimeException {
    public static final String MESSAGE = "Пользователь не найден";

    public UserNotFoundException(Long id) {
        super(MESSAGE + ", id=" + id);
    }

    public UserNotFoundException(String username) {
        super(MESSAGE + ", username=" + username);
    }
}
