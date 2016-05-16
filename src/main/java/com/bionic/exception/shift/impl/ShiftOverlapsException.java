package com.bionic.exception.shift.impl;

import com.bionic.exception.shift.ShiftException;
import com.bionic.model.Shift;

import java.util.List;

/**
 * @author taras.yaroshchuk
 */
public class ShiftOverlapsException extends ShiftException {

    public ShiftOverlapsException(List<Shift> overlappedShifts) {
        super(String.format("This shift overlaps %d your other shift(s)", overlappedShifts.size()));
    }
}