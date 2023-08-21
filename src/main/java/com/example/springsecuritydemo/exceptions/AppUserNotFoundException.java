package com.example.reactdemoback.exceptions;

public class AppUserNotFoundException extends RuntimeException {

    public AppUserNotFoundException(String message) {
        super(message);
    }

    public AppUserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
