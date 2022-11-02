package com.kh.fitness.exception;

public class PasswordMatchException extends RuntimeException {

    public PasswordMatchException(String message) {
        super(message);
    }
}
