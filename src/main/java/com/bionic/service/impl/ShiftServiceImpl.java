package com.bionic.service.impl;

import com.bionic.dao.ShiftDao;

import com.bionic.model.Shift;
import com.bionic.service.ShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author taras.yaroshchuk
 */
@Service
public class ShiftServiceImpl implements ShiftService {

    @Autowired
    private ShiftDao shiftDao;

    @Override
    public Shift addShift(Shift shift) {
        return shiftDao.saveAndFlush(shift);
    }


    @Override
    public void delete(int id)  {         shiftDao.delete(id);
    }

    @Override
    public Shift getById(int id) {
        return shiftDao.findOne(id);
    }

    @Override
    public Shift editShift(Shift shift) {
        return shiftDao.saveAndFlush(shift);
    }

    @Override
    public List<Shift> getByUserId(int user_id) {
        return shiftDao.getByUserId(user_id);
    }

    @Override
    public void deleteByUser(int user_id) {
        shiftDao.deleteByUser(user_id);
    }
}
