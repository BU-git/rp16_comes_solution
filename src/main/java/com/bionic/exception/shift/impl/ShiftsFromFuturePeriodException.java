package com.bionic.exception.shift.impl;

import com.bionic.exception.shift.ShiftException;

/**
 * @author Pavel Boiko
 */
public class ShiftsFromFuturePeriodException extends ShiftException {

    public ShiftsFromFuturePeriodException() {
        super("Shifts for the specified period are not yet created");
    }
}
