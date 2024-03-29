package com.kh.fitness.exception;

public class PhoneAlreadyExistException extends FitnessAppException {
    public PhoneAlreadyExistException(String phone) {
        super("Пользователь c телефоном: " + phone + " уже существует");
    }
    public PhoneAlreadyExistException(String message,Object ignore) {
        super(message);
    }
}
