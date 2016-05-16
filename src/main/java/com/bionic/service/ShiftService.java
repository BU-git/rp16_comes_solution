package com.bionic.service;

import com.bionic.exception.shift.impl.ShiftOverlapsException;
import com.bionic.model.Shift;

import java.util.List;

/**
 * @author taras.yaroshchuk
 */
public interface ShiftService {

    Shift addShift(Shift shift) throws ShiftOverlapsException;

    void delete(int id) ;

    Shift getById(int id);

    Shift editShift(Shift shift);

    List<Shift> getByUserId(int user_id);

    void deleteByUser(int user_id);
}
