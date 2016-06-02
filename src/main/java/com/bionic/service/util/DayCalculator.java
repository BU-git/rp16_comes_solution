package com.bionic.service.util;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Pavel Boiko
 */
public class DayCalculator {

    public static Date getDayStartTime(Date weekStartDate, int dayNumber) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(weekStartDate);
        calendar.add(Calendar.DATE, dayNumber - 1);

        Date dayStartTime = calendar.getTime();

        return dayStartTime;
    }

    public static Date getDayEndTime(Date weekStartDate, int dayNumber) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(weekStartDate);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        calendar.add(Calendar.DATE, dayNumber - 1);

        Date dayEndTime = calendar.getTime();

        return dayEndTime;
    }

    public static Date getDayEndTime(Date day) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(day);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);

        return  calendar.getTime();
    }
}
