package com.projectx.exceptions;

public class UserNotDeletedException extends RuntimeException {

    public UserNotDeletedException() {
        super("Username or Password incorrect, user not deleted!");
    }

    public UserNotDeletedException(String message) {
        super(message);
    }
}
