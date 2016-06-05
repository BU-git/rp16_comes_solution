package com.bionic.service.impl;

import com.bionic.dao.RideDao;
import com.bionic.dao.ShiftDao;
import com.bionic.exception.shift.impl.ShiftOverlapsException;
import com.bionic.model.Ride;
import com.bionic.model.Shift;
import com.bionic.service.ShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * @author taras.yaroshchuk
 */
@Service
@Transactional
public class ShiftServiceImpl implements ShiftService {

    @Autowired
    private ShiftDao shiftDao;

    @Autowired
    private RideDao rideDao;

    @Override
    public Shift addShift(Shift shift) throws ShiftOverlapsException {
        if (shift.getRides() != null) {
            for (Ride ride : shift.getRides()) {
                ride.setShift(shift);
            }
        }

        int userId = shift.getUser().getId();
        List<Shift> overlappedShifts = shiftDao.getOverlappedShifts(userId, shift.getStartTime(), shift.getEndTime());
        if (!ObjectUtils.isEmpty(overlappedShifts))
            throw new ShiftOverlapsException(overlappedShifts);
        return shiftDao.saveAndFlush(shift);
    }


    @Override
    public void delete(int id)  { shiftDao.delete(id); }

    @Override
    public Shift getById(int id) {
        return shiftDao.findOne(id);
    }

    @Override
    @Transactional
    public Shift editShift(Shift shift) throws ShiftOverlapsException {
        int userId = shift.getUser().getId();
        int shiftId = shift.getId();
        List<Shift> overlappedShifts = shiftDao.getOverlappedShifts(userId, shiftId, shift.getStartTime(), shift.getEndTime());
        if (!ObjectUtils.isEmpty(overlappedShifts))
            throw new ShiftOverlapsException(overlappedShifts);

        if (shift.getRides() != null) {
            for (Ride ride : shift.getRides()) {
                ride.setShift(shift);
            }
        }

        Shift existingShift = shiftDao.findOne(shift.getId());
        for (Ride rExist : existingShift.getRides()) {
            boolean deletedRide = true;
            for (Ride rNew : shift.getRides()){
                if (rExist.equals(rNew)) deletedRide = false;
            }
            if (deletedRide) rideDao.deleteById(rExist.getId());
        }

        return shiftDao.saveAndFlush(shift);
    }

    @Override
    public List<Shift> getByUserId(int user_id) {
        return shiftDao.getByUserId(user_id);
    }

    @Override
    @Transactional
    public void deleteByUser(int user_id) {
        shiftDao.deleteByUser(user_id);
    }
}
