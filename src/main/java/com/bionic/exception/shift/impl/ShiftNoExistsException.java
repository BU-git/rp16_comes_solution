/**
 * Check Shift Exists
 */
package com.bionic.exception.shift.impl;

import com.bionic.exception.shift.ShiftException;

/**
 * @author vitalii.levash
 */
public class ShiftNoExistsException extends ShiftException {

    public ShiftNoExistsException(final int id) {
        super("Shift with ID Does not exists:"+id);
    }
}
