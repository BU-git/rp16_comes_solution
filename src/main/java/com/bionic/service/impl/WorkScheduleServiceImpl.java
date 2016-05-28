package com.bionic.service.impl;

import com.bionic.dao.WorkScheduleDao;
import com.bionic.model.WorkSchedule;
import com.bionic.service.WorkScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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
    public void delete(WorkSchedule workSchedule) {
        workScheduleDao.delete(workSchedule.getId());
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

    public int getContractHours(WorkSchedule workSchedule) {
        int hours = 0;
        if (workSchedule.getSunday() != null) hours += workSchedule.getSunday();
        if (workSchedule.getMonday() != null) hours += workSchedule.getMonday();
        if (workSchedule.getTuesday() != null) hours += workSchedule.getTuesday();
        if (workSchedule.getWednesday() != null) hours += workSchedule.getWednesday();
        if (workSchedule.getThursday() != null) hours += workSchedule.getThursday();
        if (workSchedule.getFriday() != null) hours += workSchedule.getFriday();
        if (workSchedule.getSaturday() != null) hours += workSchedule.getSaturday();

        return hours;
    }

    @Override
    public WorkSchedule getActualWorkSchedule(int uer_id, Date date) {
        return workScheduleDao.getActualWorkSchedule(uer_id, date);
    }

    @Override
    public int getContractHoursForWeek(int userId, Date weekStartDate) {
        WorkSchedule workSchedule = getActualWorkSchedule(userId, weekStartDate);
        int contractHours = 0;
        contractHours += workSchedule.getMonday();
        contractHours += workSchedule.getTuesday();
        contractHours += workSchedule.getWednesday();
        contractHours += workSchedule.getThursday();
        contractHours += workSchedule.getFriday();
        contractHours += workSchedule.getSaturday();
        contractHours += workSchedule.getSunday();
        return contractHours;
    }
}
