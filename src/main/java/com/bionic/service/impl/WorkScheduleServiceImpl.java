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

    @Override
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
    public int getContractHoursForWorkingWeek(WorkSchedule workSchedule) {
        int hours = 0;
        if (workSchedule.getMonday() != null) hours += workSchedule.getMonday();
        if (workSchedule.getTuesday() != null) hours += workSchedule.getTuesday();
        if (workSchedule.getWednesday() != null) hours += workSchedule.getWednesday();
        if (workSchedule.getThursday() != null) hours += workSchedule.getThursday();
        if (workSchedule.getFriday() != null) hours += workSchedule.getFriday();

        return hours;
    }

    @Override
    public WorkSchedule getActualWorkSchedule(int userId, Date date) {
        WorkSchedule actualWorkSchedule = workScheduleDao.getActualWorkSchedule(userId, date);
        if (actualWorkSchedule == null) {
            actualWorkSchedule = workScheduleDao.getOldestWorkSchedule(userId);
        }
        return actualWorkSchedule;
    }

    @Override
    public int getContractHoursForWeek(int userId, Date weekStartDate) {
        WorkSchedule workSchedule = getActualWorkSchedule(userId, weekStartDate);
        int contractHours = getContractHours(workSchedule);
        return contractHours;
    }

    @Override
    public int getContractHoursForWorkingWeek(int userId, Date weekStartDate) {
        WorkSchedule workSchedule = getActualWorkSchedule(userId, weekStartDate);
        int contractHours = getContractHoursForWorkingWeek(workSchedule);
        return contractHours;
    }
}
