package com.bionic.service.util;

import java.util.Calendar;
import java.util.Date;

import static com.bionic.service.util.PeriodCalculator.NUMBER_OF_WEEKS_IN_PERIOD;

/**
 * @author Pavel Boiko
 */
public class WeekCalculator {

    public static Date getWeekStartTime(int year, int period, int week) {

        int startWeek = period * NUMBER_OF_WEEKS_IN_PERIOD + week;

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, Calendar.JANUARY, 0, 0, 0, 0);
        calendar.set(Calendar.WEEK_OF_YEAR, startWeek);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        Date startDate = calendar.getTime();
        System.out.println(startDate);

        return startDate;
    }

    public static Date getWeekEndTime(int year, int period, int week) {

        int endWeek = period * NUMBER_OF_WEEKS_IN_PERIOD + week;

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, Calendar.JANUARY, 0, 23, 59, 59);
        calendar.set(Calendar.WEEK_OF_YEAR, endWeek);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);

        Date endDate = calendar.getTime();
        System.out.println(endDate);

        return endDate;
    }

    public static int getWeekOfYear(int year, int period, int week) {

        int startWeek = period * NUMBER_OF_WEEKS_IN_PERIOD + week;

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, Calendar.JANUARY, 0, 0, 0, 0);
        calendar.set(Calendar.WEEK_OF_YEAR, startWeek);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);

        return weekOfYear;
    }
}
