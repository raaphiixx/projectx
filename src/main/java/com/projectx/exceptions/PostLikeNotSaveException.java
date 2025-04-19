package com.projectx.exceptions;

public class PostLikeNotSaveException extends RuntimeException {

    public PostLikeNotSaveException() {
        super("Like not Save");
    }


    public PostLikeNotSaveException(String message) {
        super(message);
    }
}
