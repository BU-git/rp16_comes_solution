package com.bionic.exception.shift.impl;

import com.bionic.exception.shift.ShiftException;

/**
 * @author Pavel Boiko
 */
public class ShiftsNotFoundException extends ShiftException {

    public ShiftsNotFoundException() {
        super("Shifts for the specified period not found");
    }
}

