package com.bionic.service.util;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Pavel Boiko
 */
public class MonthCalculator {

    public static Date getMonthStartTime(int year, int month) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, 1, 0, 0, 0);
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            calendar.add(Calendar.DATE, 1);
        }
        Date startDate = calendar.getTime();
        System.out.println(startDate);

        return startDate;
    }

    public static Date getMonthEndTime(int year, int month) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, 1, 23, 59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, daysInMonth);
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
            calendar.add(Calendar.DATE, 1);
        }
        Date endDate = calendar.getTime();
        System.out.println(endDate);

        return endDate;
    }

}
