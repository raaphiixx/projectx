package com.projectx.exceptions;

public class UserNotAuthorizationException extends RuntimeException {

    public UserNotAuthorizationException() {
        super("User Not Authorized!");
    }

    public UserNotAuthorizationException(String message) {
        super(message);
    }
}
