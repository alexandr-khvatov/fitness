package com.kh.fitness.exception;

public class EmailAlreadyExistException extends FitnessAppException {
    public EmailAlreadyExistException(String email) {
        super("Пользователь c email: " + email + " уже существует");
    }
}
