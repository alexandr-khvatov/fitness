package com.kh.fitness.exception;

public class UserNotFoundException extends FitnessAppException {
    public static final String MESSAGE = "User not found";

    public UserNotFoundException(Long id) {
        super(MESSAGE + ", id=" + id);
    }

    public UserNotFoundException(String username) {
        super(MESSAGE + ", username=" + username);
    }
}
