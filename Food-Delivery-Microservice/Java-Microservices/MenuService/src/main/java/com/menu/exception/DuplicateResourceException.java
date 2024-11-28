package com.menu.exception;

public class DuplicateResourceException extends RuntimeException{
    public DuplicateResourceException(String message) {
        super(message);
    }

    public DuplicateResourceException() {
        super();
    }
}
