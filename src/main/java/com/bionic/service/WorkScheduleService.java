package com.bionic.service;

import com.bionic.model.WorkSchedule;

import java.util.List;

/**
 * Created by Kliakhin on 03/17/16.
 */

public interface WorkScheduleService {
    WorkSchedule addWorkSchedule(WorkSchedule workSchedule);
    void delete(int id);
    WorkSchedule getById(int id);
    WorkSchedule editWorchedule(WorkSchedule workSchedule);
    List<WorkSchedule> getAll();
}
