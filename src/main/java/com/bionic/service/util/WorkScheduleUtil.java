package com.bionic.service.util;

import com.bionic.model.User;
import com.bionic.model.WorkSchedule;

import java.util.Date;

public class WorkScheduleUtil {

    public static WorkSchedule createEmptyWorkSchedule(User user) {
        WorkSchedule workSchedule = new WorkSchedule();
        workSchedule.setCreationTime(new Date());
        workSchedule.setDeactivationTime(new Date(new Date().getTime() * 2));
        workSchedule.setSunday(0);
        workSchedule.setSaturday(0);
        workSchedule.setMonday(0);
        workSchedule.setTuesday(0);
        workSchedule.setWednesday(0);
        workSchedule.setThursday(0);
        workSchedule.setFriday(0);
        workSchedule.setUserId(user.getId());
        return workSchedule;
    }
}
