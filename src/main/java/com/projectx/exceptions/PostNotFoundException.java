package com.projectx.exceptions;

public class PostNotFoundException extends RuntimeException{

    public PostNotFoundException() {
        super("Post ID Not Found!");
    }

    public PostNotFoundException(String message) {
        super(message);
    }
}
