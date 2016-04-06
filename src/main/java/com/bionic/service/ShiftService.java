package com.bionic.service;

import com.bionic.model.Shift;

import java.util.List;

/**
 * @author taras.yaroshchuk
 */
public interface ShiftService {

    Shift addShift(Shift shift);

    void delete(int id);

    Shift getById(int id);

    Shift editShift(Shift shift);

    List<Shift> getByUserId(int user_id);
}
