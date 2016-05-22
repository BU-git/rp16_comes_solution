package com.bionic.service.util;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Pavel Boiko
 */
public class PeriodCalculator {

    public static final int NUMBER_OF_WEEKS_IN_PERIOD = 4;

    public static Date getPeriodStartTime(int year, int period) {

        int startWeek = period * NUMBER_OF_WEEKS_IN_PERIOD + 1;

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, Calendar.JANUARY, 0, 0, 0, 0);

        Calendar offsetCalendar = Calendar.getInstance();
        offsetCalendar.set(Calendar.YEAR, year);
        offsetCalendar.set(Calendar.DAY_OF_YEAR, 1);

        if (offsetCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
            calendar.set(Calendar.WEEK_OF_YEAR, startWeek);
        } else {
            calendar.set(Calendar.WEEK_OF_YEAR, startWeek + 1);
        }

        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        Date startDate = calendar.getTime();
        System.out.println(startDate);

        return startDate;
    }

    public static Date getPeriodEndTime(int year, int period) {

        int startWeek = period * NUMBER_OF_WEEKS_IN_PERIOD + 1;
        int endWeek = startWeek + NUMBER_OF_WEEKS_IN_PERIOD - 1;

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, Calendar.JANUARY, 0, 23, 59, 59);
        calendar.set(Calendar.MILLISECOND, 999);

        Calendar offsetCalendar = Calendar.getInstance();
        offsetCalendar.set(Calendar.YEAR, year);
        offsetCalendar.set(Calendar.DAY_OF_YEAR, 1);

        if (offsetCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
            calendar.set(Calendar.WEEK_OF_YEAR, endWeek);
        } else {
            calendar.set(Calendar.WEEK_OF_YEAR, endWeek + 1);
        }

        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);

        Date endDate = calendar.getTime();
        System.out.println(endDate);

        return endDate;
    }

    public static int getPauseTime(long workedTime) {

        if(workedTime >= 270 * 60 * 1000 && workedTime < 450 * 60 * 1000 ) return 30 * 60 * 1000 ;
        if(workedTime >= 450 * 60 * 1000 && workedTime < 630 * 60 * 1000) return 60 * 60 * 1000;
        if(workedTime >= 630 * 60 * 1000 && workedTime < 810 * 60 * 1000) return 90 * 60 * 1000;
        if(workedTime >= 810 * 60 * 1000 && workedTime < 990 * 60 * 1000) return 120 * 60 * 1000;
        if(workedTime >= 990 * 60 * 1000) return 150 * 60 * 1000;

        return 0;
    }

}
