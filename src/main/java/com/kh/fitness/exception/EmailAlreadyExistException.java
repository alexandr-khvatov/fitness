package com.kh.fitness.exception;

public class EmailAlreadyExistException extends FitnessAppException {
    public EmailAlreadyExistException(String email) {
        super("Пользователь c email: " + email + " уже существует");
    }

    public EmailAlreadyExistException(String message,Object ignore) {
        super(message);
    }
}
