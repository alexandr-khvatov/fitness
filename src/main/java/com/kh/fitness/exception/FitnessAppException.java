package com.kh.fitness.exception;

public class FitnessAppException extends RuntimeException {
    public FitnessAppException() {
    }

    public FitnessAppException(String message) {
        super(message);
    }

    public FitnessAppException(String message, Throwable cause) {
        super(message, cause);
    }

    public FitnessAppException(Throwable cause) {
        super(cause);
    }
}
