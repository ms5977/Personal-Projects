package com.main.restaurant.exception;

public class DuplicateResourceException extends RuntimeException{
    public DuplicateResourceException(String message) {
        super(message);
    }

    public DuplicateResourceException() {
        super();
    }
}
