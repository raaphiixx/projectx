package com.projectx.exceptions;

public class PostNotCreatedException extends RuntimeException {

    public PostNotCreatedException() {
        super("Post Not Created");
    }


    public PostNotCreatedException(String message) {
        super(message);
    }
}
