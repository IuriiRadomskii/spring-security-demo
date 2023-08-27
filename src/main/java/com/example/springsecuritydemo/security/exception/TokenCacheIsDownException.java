package com.example.springsecuritydemo.security.exception;

public class TokenCacheIsDownException extends RuntimeException {

    public TokenCacheIsDownException(String message) {
        super(message);
    }

    public TokenCacheIsDownException(String message, Throwable cause) {
        super(message, cause);
    }
}
