package com.kh.fitness.exception;

public class UserRoleException extends FitnessAppException {
    public UserRoleException() {
    }

    public UserRoleException(String message) {
        super(message);
    }

    public UserRoleException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserRoleException(Throwable cause) {
        super(cause);
    }
}
