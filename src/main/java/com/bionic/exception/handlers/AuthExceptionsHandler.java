package com.bionic.exception.handlers;

import com.bionic.exception.auth.impl.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * author Dima Budko
 * v.0.1
 */
@ControllerAdvice
public class AuthExceptionsHandler {

    @ExceptionHandler(UserNotExistsException.class)
    public ResponseEntity handleUserNotExistException(UserNotExistsException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }


    @ExceptionHandler(UserExistsException.class)
    public ResponseEntity handleUserExistException(UserExistsException e) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(e.getMessage());
    }

    @ExceptionHandler(PasswordIncorrectException.class)
    public ResponseEntity handlePasswordIncorrectException(PasswordIncorrectException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    @ExceptionHandler(CredentialsExpired.class)
    public ResponseEntity handleCredentialsExpired(CredentialsExpired e) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(e.getMessage());
    }

    @ExceptionHandler(TemporaryPassword.class)
    public ResponseEntity handleTemporaryPassword(TemporaryPassword e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(e.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity handleAccountStatusException(AuthenticationException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(e.getMessage());
    }

    @ExceptionHandler(UserNotVerifiedException.class)
    public ResponseEntity handleCredentialsExpired(UserNotVerifiedException e) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(e.getMessage());
    }
}
