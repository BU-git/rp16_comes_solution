package com.bionic.service.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static com.bionic.service.util.PeriodCalculator.NUMBER_OF_WEEKS_IN_PERIOD;

/**
 * @author Pavel Boiko
 */
public class WeekCalculator {

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

        int startWeek = (period - 1) * NUMBER_OF_WEEKS_IN_PERIOD + week;

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, Calendar.JANUARY, 0, 0, 0, 0);
        calendar.set(Calendar.WEEK_OF_YEAR, startWeek);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        Date startDate = calendar.getTime();
        System.out.println(startDate);

        return startDate;
    }

    public static Date getPeriodWeekEndTime(int year, int period, int week) {

        int endWeek = (period - 1) * NUMBER_OF_WEEKS_IN_PERIOD + week;

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, Calendar.JANUARY, 0, 23, 59, 59);
        calendar.set(Calendar.WEEK_OF_YEAR, endWeek);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);

        Date endDate = calendar.getTime();
        System.out.println(endDate);

        return endDate;
    }

    public static int getPeriodWeekOfYear(int year, int period, int week) {

        int startWeek = (period - 1) * NUMBER_OF_WEEKS_IN_PERIOD + week;

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, Calendar.JANUARY, 0, 0, 0, 0);
        calendar.set(Calendar.WEEK_OF_YEAR, startWeek);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);

        return weekOfYear;
    }

    public static int getMonthWeekOfYear(int year, int month, int week) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, 1, 0, 0, 0);
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            calendar.add(Calendar.DATE, 1);
        }
        calendar.add(Calendar.WEEK_OF_YEAR, week - 1);
        int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);

        return weekOfYear;
    }
}
