package com.bionic.service.impl;

import com.bionic.dao.RideDao;
import com.bionic.model.Ride;
import com.bionic.service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author taras.yaroshchuk
 */
@Service
public class RideServiceImpl implements RideService {

    @Autowired
    private RideDao rideDao;

    @Override
    public Ride addRide(Ride ride) {
        return rideDao.saveAndFlush(ride);
    }

    @Override
    public void delete(int id) {
        rideDao.delete(id);
    }

    @Override
    public Ride getById(int id) {
        return rideDao.getOne(id);
    }

    @Override
    public Ride editRide(Ride ride) {
        return rideDao.saveAndFlush(ride);
    }

    @Override
    public List<Ride> getByShiftId(int shift_id) {
        return rideDao.getByShiftId(shift_id);
    }
}
