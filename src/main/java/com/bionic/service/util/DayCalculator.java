package com.bionic.service.util;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Pavel Boiko
 */
public class DayCalculator {

    public static boolean isNationalHoliday(Date dayStartDate) {

        Calendar newYearsDay = Calendar.getInstance();
        newYearsDay.set(Calendar.MONTH, Calendar.JANUARY);
        newYearsDay.set(Calendar.DAY_OF_MONTH, 1);

        Calendar kingsDay = Calendar.getInstance();
        kingsDay.set(Calendar.MONTH, Calendar.APRIL);
        kingsDay.set(Calendar.DAY_OF_MONTH, 27);

        Calendar firstChristmasDay = Calendar.getInstance();
        firstChristmasDay.set(Calendar.MONTH, Calendar.DECEMBER);
        firstChristmasDay.set(Calendar.DAY_OF_MONTH, 25);

        Calendar secondChristmasDay = Calendar.getInstance();
        secondChristmasDay.set(Calendar.MONTH, Calendar.DECEMBER);
        secondChristmasDay.set(Calendar.DAY_OF_MONTH, 26);

        Calendar oldYearsDay = Calendar.getInstance();
        oldYearsDay.set(Calendar.MONTH, Calendar.DECEMBER);
        oldYearsDay.set(Calendar.DAY_OF_MONTH, 31);

        Calendar inputDate = Calendar.getInstance();
        inputDate.setTime(dayStartDate);

        if (inputDate.get(Calendar.DAY_OF_YEAR) == newYearsDay.get(Calendar.DAY_OF_YEAR) ||
            inputDate.get(Calendar.DAY_OF_YEAR) == kingsDay.get(Calendar.DAY_OF_YEAR) ||
            inputDate.get(Calendar.DAY_OF_YEAR) == firstChristmasDay.get(Calendar.DAY_OF_YEAR) ||
            inputDate.get(Calendar.DAY_OF_YEAR) == secondChristmasDay.get(Calendar.DAY_OF_YEAR) ||
            inputDate.get(Calendar.DAY_OF_YEAR) == oldYearsDay.get(Calendar.DAY_OF_YEAR)) return true;

        return false;
    }

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

    public static boolean rideTraversesTwoDays(Date rideStartTime, Date rideEndTime) {
        Calendar start = Calendar.getInstance();
        start.setTime(rideStartTime);
        Calendar end = Calendar.getInstance();
        end.setTime(rideEndTime);

        boolean rideTraversesTwoDays = true;
        if (start.get(Calendar.DAY_OF_MONTH) == end.get(Calendar.DAY_OF_MONTH)) rideTraversesTwoDays = false;
        return rideTraversesTwoDays;
    }
}
