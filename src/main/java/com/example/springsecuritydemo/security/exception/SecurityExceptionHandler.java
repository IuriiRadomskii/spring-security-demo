package com.example.springsecuritydemo.security.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class SecurityExceptionHandler {

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<String> handleUserAlreadyExistsException(AlreadyExistsException e) {
        return new ResponseEntity<>(e.getMessage(), null, 409);
    }

}
