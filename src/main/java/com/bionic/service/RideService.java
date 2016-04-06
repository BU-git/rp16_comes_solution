package com.bionic.service;

import com.bionic.model.Ride;

import java.util.List;

/**
 * @author taras.yaroshchuk
 */
public interface RideService {

    Ride addRide(Ride ride);

    void delete(int id);

    Ride getById(int id);

    Ride editRide(Ride ride);

    List<Ride> getByShiftId(int shift_id);
}
