package com.bionic.exception.handlers;

import com.bionic.exception.shift.impl.ShiftOverlapsException;
import com.bionic.exception.shift.impl.ShiftsFromFuturePeriodException;
import com.bionic.exception.shift.impl.ShiftsNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author Pavel Boiko
 */
@ControllerAdvice
public class ShiftExceptionsHandler {

    @ExceptionHandler(ShiftsNotFoundException.class)
    public ResponseEntity handleShiftsNotFoundException(ShiftsNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }

    @ExceptionHandler(ShiftsFromFuturePeriodException.class)
    public ResponseEntity handleShiftsFromFuturePeriodException(ShiftsFromFuturePeriodException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    @ExceptionHandler(ShiftOverlapsException.class)
    public ResponseEntity handleShiftOverlapsException(ShiftOverlapsException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT) //409
                .body(e.getMessage());
    }
}
