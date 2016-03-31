package com.bionic.service.impl;

import com.bionic.dao.WorkScheduleDao;
import com.bionic.model.WorkSchedule;
import com.bionic.service.WorkScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Kliakhin on 03/17/16.
 */
@Service
public class WorkScheduleServiceImpl implements WorkScheduleService {

    @Autowired
    private WorkScheduleDao workScheduleDao;

    @Override
    public WorkSchedule addWorkSchedule(WorkSchedule workSchedule) {
        return workScheduleDao.saveAndFlush(workSchedule);
    }

    @Override
    public void delete(int id) {
        workScheduleDao.delete(id);
    }

    @Override
    public WorkSchedule getById(int id) {
        return workScheduleDao.findOne(id);
    }

    @Override
    public WorkSchedule editWorkSchedule(WorkSchedule workSchedule) {
        return workScheduleDao.saveAndFlush(workSchedule);
    }

    @Override
    public List<WorkSchedule> getAll() {
        return workScheduleDao.findAll();
    }

    @Override
    public WorkSchedule getByUserId(int user_id) {
        return workScheduleDao.findByUserId(user_id);
    }

    @Override
    public WorkSchedule saveWorkSchedule(WorkSchedule workSchedule) {
        return workScheduleDao.saveAndFlush(workSchedule);
    }
}
