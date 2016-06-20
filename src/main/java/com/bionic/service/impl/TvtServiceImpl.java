package com.bionic.service.impl;

import com.bionic.dao.DayTypeDao;
import com.bionic.dao.ShiftDao;
import com.bionic.dto.OvertimeDTO;
import com.bionic.dto.TvtBuildDTO;
import com.bionic.dto.TvtPaidDTO;
import com.bionic.dto.WorkingWeekDTO;
import com.bionic.model.Shift;
import com.bionic.model.User;
import com.bionic.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.bionic.service.util.MonthCalculator.*;
import static com.bionic.service.util.PeriodCalculator.*;
import static com.bionic.service.util.WeekCalculator.*;

/**
 * @author Pavel Boiko
 */
@Service
public class TvtServiceImpl implements TvtService {

    @Autowired
    private UserService userService;

    @Autowired
    private OvertimeService overtimeService;

    @Autowired
    private SummaryService summaryService;

    @Autowired
    private WorkScheduleService workScheduleService;

    @Autowired
    private ShiftDao shiftDao;

    @Autowired
    private DayTypeDao dayTypeDao;

    @Override
    public List<TvtPaidDTO> getTvtPaidForYear(int userId, int year, int endingPeriod) {

        List<TvtPaidDTO> tvt = new ArrayList<>();

        for (int period = 0; period <= endingPeriod; period++) {
            TvtPaidDTO tvtPaidDTO = getTvtPaidForPeriod(userId, year, period);
            tvt.add(tvtPaidDTO);
        }

        return tvt;
    }

    public TvtPaidDTO getTvtPaidForPeriod(int userId, int year, int period) {

        User user = userService.findById(userId);
        TvtPaidDTO tvtPaidDTO = new TvtPaidDTO();
        WorkingWeekDTO overtimeSum = new WorkingWeekDTO();

        try {
            if (user.isFourWeekPayOff()) {
                tvtPaidDTO.setPeriodName("Period " + (period + 1));
                tvtPaidDTO.setFromWeek(getPeriodWeekOfYear(year, period, 1));
                tvtPaidDTO.setUntilWeek(getPeriodWeekOfYear(year, period, NUMBER_OF_WEEKS_IN_PERIOD));

                overtimeSum = summaryService.getSummaryTotal(summaryService.getSummaryForPeriod(userId, year, period));
            } else {
                tvtPaidDTO.setPeriodName(getMonthName(period));
                Date monthStartTime = getMonthStartTime(year, period);
                Date monthEndTime = getMonthEndTime(year, period);
                int numberOfWeeks = getWeeksBetween(monthStartTime, monthEndTime);
                tvtPaidDTO.setFromWeek(getMonthWeekOfYear(year, period, 1));
                tvtPaidDTO.setUntilWeek(getMonthWeekOfYear(year, period, numberOfWeeks));

                overtimeSum = summaryService.getSummaryTotal(summaryService.getSummaryForMonth(userId, year, period));
            }
        } catch (Exception e) {

        }

        int tvtLimitHours = user.getTvt();
        double totalHours = overtimeSum.getWorkedTime() / MILLIS_IN_HOUR;

        if (totalHours > tvtLimitHours) {
            if (tvtLimitHours == 220) {
                tvtPaidDTO.setAboveMandatory(totalHours - 220);
            } else {
                tvtPaidDTO.setAboveVoluntary(totalHours - tvtLimitHours);
            }

            if (user.isFourWeekPayOff()) {
                tvtPaidDTO = getPercentsForPeriod(tvtPaidDTO, userId, year, period);
            } else {
                tvtPaidDTO = getPercentsForMonth(tvtPaidDTO, userId, year, period);
            }

        }

        return tvtPaidDTO;
    }

    private TvtPaidDTO getPercentsForPeriod(TvtPaidDTO tvtPaidDTO, int userId, int year, int period) {
        Date periodStartTime = getPeriodStartTime(year, period);
        Date periodEndTime = getPeriodEndTime(year, period);

        User user = userService.findById(userId);
        List<Shift> shifts = shiftDao.getForPeriod(userId, periodStartTime, periodEndTime);
        int tvtLimitHours = user.getTvt();
        long tvtLimitTime = tvtLimitHours * 1000 * 60 * 60;

        long totalTime = 0;

        for (int week = 1; week <= NUMBER_OF_WEEKS_IN_PERIOD; week++) {
            Date weekStartTime = getPeriodWeekStartTime(year, period, week);
            Date weekEndTime = getPeriodWeekEndTime(year, period, week);
            int contractHours = 0;
            contractHours = workScheduleService.getContractHoursForWorkingWeek(userId, weekStartTime);
            if (contractHours == 0) contractHours = 40;
            long contractTime = contractHours * 60 * 60 * 1000;

            long weekTotalTime = overtimeService.getWorkedTimeForPeriod(shifts, weekStartTime, weekEndTime);

            if (totalTime > tvtLimitTime) {
                long startTime = 0;
                tvtPaidDTO = getPercentsForWeek(tvtPaidDTO, shifts, weekStartTime, weekEndTime, contractTime, startTime);
            } else if ((totalTime + weekTotalTime) > tvtLimitTime) {
                long startTime = tvtLimitTime - totalTime;
                tvtPaidDTO = getPercentsForWeek(tvtPaidDTO, shifts, weekStartTime, weekEndTime, contractTime, startTime);
                totalTime += weekTotalTime;
            } else {
                totalTime += weekTotalTime;
            }
            System.out.println("Total time = " + totalTime / MILLIS_IN_HOUR);
            System.out.println("TVT Limit time = " + tvtLimitTime / MILLIS_IN_HOUR);
        }


        return tvtPaidDTO;

    }

    private TvtPaidDTO getPercentsForMonth(TvtPaidDTO tvtPaidDTO, int userId, int year, int month) {
        Date monthStartTime = getMonthStartTime(year, month);
        Date monthEndTime = getMonthEndTime(year, month);

        User user = userService.findById(userId);
        List<Shift> shifts = shiftDao.getForPeriod(userId, monthStartTime, monthEndTime);
        int tvtLimitHours = user.getTvt();
        long tvtLimitTime = tvtLimitHours * 1000 * 60 * 60;

        int numberOfWeeks = getWeeksBetween(monthStartTime, monthEndTime);

        long totalTime = 0;
        for (int week = 1; week <= numberOfWeeks; week++) {
            Date weekStartTime = getMonthWeekStartTime(year, month, week);
            Date weekEndTime = getMonthWeekEndTime(year, month, week);

            int contractHours = workScheduleService.getContractHoursForWorkingWeek(userId, weekStartTime);
            if (contractHours == 0) contractHours = 40;
            long contractTime = contractHours * 60 * 60 * 1000;

            long weekTotalTime = overtimeService.getWorkedTimeForPeriod(shifts, weekStartTime, weekEndTime);
            System.out.println("TVT MONTH WEEK " + week + " = " + weekTotalTime);

            if (totalTime > tvtLimitTime) {
                long startTime = 0;
                tvtPaidDTO = getPercentsForWeek(tvtPaidDTO, shifts, weekStartTime, weekEndTime, contractTime, startTime);
            } else if ((totalTime + weekTotalTime) > tvtLimitTime) {
                long startTime = tvtLimitTime - totalTime;
                System.out.println("------Total time = " + totalTime / MILLIS_IN_HOUR);
                System.out.println("------TVT Limit time = " + tvtLimitTime / MILLIS_IN_HOUR);
                System.out.println("------Start time = " + startTime / MILLIS_IN_HOUR);
                tvtPaidDTO = getPercentsForWeek(tvtPaidDTO, shifts, weekStartTime, weekEndTime, contractTime, startTime);
                totalTime += weekTotalTime;
            } else {
                totalTime += weekTotalTime;
            }
        }

        return tvtPaidDTO;
    }

    private TvtPaidDTO getPercentsForWeek(TvtPaidDTO tvtPaidDTO, List<Shift> shifts,
                                          Date weekStartTime, Date weekEndTime, long contractTime, long startTime) {

        Date workingWeekEndTime = getWorkingWeekEndTime(weekEndTime);
        Date saturdayStartTime = getSaturdayStartTime(weekStartTime);
        Date saturdayEndTime = getSaturdayEndTime(weekEndTime);
        Date sundayStartTime = getSundayStartTime(weekStartTime);

        double paid130 = 0.0;
        double paid150 = 0.0;

        long actualWorkedTime = overtimeService.getWorkedTimeForPeriod(shifts, weekStartTime, workingWeekEndTime);
        long saturdayWorkedTime = overtimeService.getWorkedTimeForPeriod(shifts, saturdayStartTime, saturdayEndTime);
        long sundayWorkedTime = overtimeService.getWorkedTimeForPeriod(shifts, sundayStartTime, weekEndTime);

        System.out.println("Actual worked time = " + actualWorkedTime / MILLIS_IN_HOUR);
        System.out.println("Saturday worked time = " + saturdayWorkedTime / MILLIS_IN_HOUR);
        System.out.println("Sunday worked time = " + saturdayWorkedTime / MILLIS_IN_HOUR);
        System.out.println("Start worked time = " + startTime / MILLIS_IN_HOUR);

        if (startTime == 0) {
            paid130 = (actualWorkedTime + saturdayWorkedTime) / MILLIS_IN_HOUR;
            paid150 = sundayWorkedTime / MILLIS_IN_HOUR;
        } else {
            long temp130 = 0;
            long temp150 = 0;

            if (actualWorkedTime > startTime) {
                temp130 = actualWorkedTime - startTime;
                temp130 += saturdayWorkedTime;
                temp150 = sundayWorkedTime;
            } else if ((actualWorkedTime + saturdayWorkedTime) > startTime) {
                temp130 = actualWorkedTime + saturdayWorkedTime - startTime;
                temp150 = sundayWorkedTime;
            } else {
                temp150 = actualWorkedTime + saturdayWorkedTime + sundayWorkedTime - startTime;
            }

            paid130 = temp130 / MILLIS_IN_HOUR;
            paid150 = temp150 / MILLIS_IN_HOUR;
        }

        tvtPaidDTO.setPaid130(paid130);
        tvtPaidDTO.setPaid150(paid150);

        return tvtPaidDTO;
    }

    public TvtBuildDTO getTvtBuildForPeriod(int userId, int year, int period) {

        User user = userService.findById(userId);
        TvtBuildDTO tvtBuildDTO = new TvtBuildDTO();

        if (user.isFourWeekPayOff()) {
            tvtBuildDTO.setPeriodName("Period " + (period + 1));
            tvtBuildDTO.setFromWeek(getPeriodWeekOfYear(year, period, 1));
            tvtBuildDTO.setUntilWeek(getPeriodWeekOfYear(year, period, NUMBER_OF_WEEKS_IN_PERIOD));

            OvertimeDTO overtimeSum = overtimeService.getOvertimeSum(overtimeService.getOvertimeForPeriod(userId, year, period));

        } else {
            tvtBuildDTO.setPeriodName(getMonthName(period));
            Date monthStartTime = getMonthStartTime(year, period);
            Date monthEndTime = getMonthEndTime(year, period);
            int numberOfWeeks = getWeeksBetween(monthStartTime, monthEndTime);
            tvtBuildDTO.setFromWeek(getMonthWeekOfYear(year, period, 1));
            tvtBuildDTO.setUntilWeek(getMonthWeekOfYear(year, period, numberOfWeeks));

            OvertimeDTO overtimeSum = overtimeService.getOvertimeSum(overtimeService.getOvertimeForMonth(userId, year, period));

        }

        return tvtBuildDTO;
    }
    @Override
    public List<TvtBuildDTO> getTvtBuildForYear(int userId, int year, int endingPeriod) {

        List<TvtBuildDTO> tvt = new ArrayList<>();

        for (int period = 0; period <= endingPeriod; period++) {
            TvtBuildDTO tvtBuildDTO = getTvtBuildForPeriod(userId, year, period);
            tvt.add(tvtBuildDTO);
        }

        return tvt;
    }
}
