package com.bionic.service.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static com.bionic.service.util.PeriodCalculator.NUMBER_OF_WEEKS_IN_PERIOD;

/**
 * @author Pavel Boiko
 */
public class WeekCalculator {

    public static final double MILLIS_IN_HOUR = 1000 * 60 *60d;

    public static Date getWorkingWeekEndTime(Date weekEndTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(weekEndTime);
        calendar.add(Calendar.DAY_OF_WEEK, -2);

        Date workingWeekEndTime = calendar.getTime();
        return workingWeekEndTime;
    }

    public static Date getSaturdayEndTime(Date weekEndTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(weekEndTime);
        calendar.add(Calendar.DAY_OF_WEEK, -1);

        Date workingWeekEndTime = calendar.getTime();
        return workingWeekEndTime;
    }

    public static Date getSaturdayStartTime(Date weekStartTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(weekStartTime);
        calendar.add(Calendar.DAY_OF_WEEK, 5);

        Date workingWeekEndTime = calendar.getTime();
        return workingWeekEndTime;
    }

    public static Date getSundayStartTime(Date weekStartTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(weekStartTime);
        calendar.add(Calendar.DAY_OF_WEEK, 6);

        Date workingWeekEndTime = calendar.getTime();
        return workingWeekEndTime;
    }

    public static Date getMonthWeekStartTime(int year, int month, int week) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, 1, 0, 0, 0);
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            calendar.add(Calendar.DATE, 1);
        }
        calendar.add(Calendar.WEEK_OF_YEAR, week - 1);
        Date startDate = calendar.getTime();
        System.out.println(startDate);

        return startDate;
    }

    public static Date getMonthWeekEndTime(int year, int month, int week) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, 1, 23, 59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            calendar.add(Calendar.DATE, 1);
        }
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
            calendar.add(Calendar.DATE, 1);
        }
        calendar.add(Calendar.WEEK_OF_YEAR, week - 1);
        Date endDate = calendar.getTime();
        System.out.println(endDate);

        return endDate;
    }

    public static int getWeeksBetween (Date a, Date b) {

        if (b.before(a)) {
            return -getWeeksBetween(b, a);
        }

        Calendar cal = new GregorianCalendar();
        cal.setTime(a);
        int weeks = 0;
        while (cal.getTime().before(b)) {
            cal.add(Calendar.WEEK_OF_YEAR, 1);
            weeks++;
        }
        return weeks;
    }

    public static Date getPeriodWeekStartTime(int year, int period, int week) {

        int startWeek = period * NUMBER_OF_WEEKS_IN_PERIOD + week;

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

    public static Date getPeriodWeekEndTime(int year, int period, int week) {

        int endWeek = period * NUMBER_OF_WEEKS_IN_PERIOD + week;

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

    public static int getPeriodWeekOfYear(int year, int period, int week) {

        int weekOfYear = period * NUMBER_OF_WEEKS_IN_PERIOD + week;

            return weekOfYear;
    }

    public static int getMonthWeekOfYear(int year, int month, int week) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, 1, 0, 0, 0);
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            calendar.add(Calendar.DATE, 1);
        }
        calendar.add(Calendar.WEEK_OF_YEAR, week - 1);

        Calendar offsetCalendar = Calendar.getInstance();
        offsetCalendar.set(Calendar.YEAR, year);
        offsetCalendar.set(Calendar.DAY_OF_YEAR, 1);

        if (offsetCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
            if (calendar.get(Calendar.WEEK_OF_YEAR) >= week) {
                return calendar.get(Calendar.WEEK_OF_YEAR);
            } else {
                return getMonthWeekOfYear(year, month, week - 1) + 1;
            }
        } else if (calendar.get(Calendar.WEEK_OF_YEAR) != 1) {
            return calendar.get(Calendar.WEEK_OF_YEAR) - 1;
        } else {
            return getMonthWeekOfYear(year, month, week - 1) + 1;
        }
    }
}
