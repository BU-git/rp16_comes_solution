package com.bionic.exception.handlers;

import com.bionic.exception.auth.impl.UserNotExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * author Dima Budko
 * v.0.1
 */
@ControllerAdvice
public class UserNotExitstHandler {

    @ExceptionHandler(UserNotExistsException.class)
    public ResponseEntity handleException(UserNotExistsException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("Custom error message in json");
    }
}
