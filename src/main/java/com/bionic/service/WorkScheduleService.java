package com.bionic.service;

import com.bionic.model.WorkSchedule;

import java.util.Date;
import java.util.List;

/**
 * Created by Kliakhin on 03/17/16.
 */
public interface WorkScheduleService {

    WorkSchedule addWorkSchedule(WorkSchedule workSchedule);

    void delete(int id);

    void delete(WorkSchedule workSchedule);

    WorkSchedule getById(int id);

    WorkSchedule editWorkSchedule(WorkSchedule workSchedule);

    List<WorkSchedule> getAll();

    WorkSchedule getByUserId(int user_id);

    WorkSchedule saveWorkSchedule(WorkSchedule workSchedule);

    int getContractHours(WorkSchedule workSchedule);

    WorkSchedule getActualWorkSchedule(int user_id, Date date);

    int getContractHoursForWeek(int userId, Date weekStartDate);

    int getContractHoursMondayFriday(WorkSchedule workSchedule);

    int getContractHoursMondayFriday(int userId, Date weekStartDate);
}
