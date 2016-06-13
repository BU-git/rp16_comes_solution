package com.bionic.service.impl;

import com.bionic.dto.OvertimeDTO;
import com.bionic.dto.TvtBuildDTO;
import com.bionic.dto.TvtPaidDTO;
import com.bionic.model.User;
import com.bionic.service.OvertimeService;
import com.bionic.service.TvtService;
import com.bionic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.bionic.service.util.MonthCalculator.getMonthEndTime;
import static com.bionic.service.util.MonthCalculator.getMonthName;
import static com.bionic.service.util.MonthCalculator.getMonthStartTime;
import static com.bionic.service.util.PeriodCalculator.NUMBER_OF_WEEKS_IN_PERIOD;
import static com.bionic.service.util.WeekCalculator.getMonthWeekOfYear;
import static com.bionic.service.util.WeekCalculator.getPeriodWeekOfYear;
import static com.bionic.service.util.WeekCalculator.getWeeksBetween;

/**
 * @author Pavel Boiko
 */
@Service
public class TvtServiceImpl implements TvtService {

    @Autowired
    private UserService userService;

    @Autowired
    private OvertimeService overtimeService;

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

        if (user.isFourWeekPayOff()) {
            tvtPaidDTO.setPeriodName("Period " + (period + 1));
            tvtPaidDTO.setFromWeek(getPeriodWeekOfYear(year, period, 1));
            tvtPaidDTO.setUntilWeek(getPeriodWeekOfYear(year, period, NUMBER_OF_WEEKS_IN_PERIOD));

            OvertimeDTO overtimeSum = overtimeService.getOvertimeSum(overtimeService.getOvertimeForPeriod(userId, year, period));
            int tvtLimitHours = user.getTvt();
            double totalHours = overtimeSum.getTotalHours();

            if (totalHours > tvtLimitHours) {

            }

        } else {
            tvtPaidDTO.setPeriodName(getMonthName(period));
            Date monthStartTime = getMonthStartTime(year, period);
            Date monthEndTime = getMonthEndTime(year, period);
            int numberOfWeeks = getWeeksBetween(monthStartTime, monthEndTime);
            tvtPaidDTO.setFromWeek(getMonthWeekOfYear(year, period, 1));
            tvtPaidDTO.setUntilWeek(getMonthWeekOfYear(year, period, numberOfWeeks));

            OvertimeDTO overtimeSum = overtimeService.getOvertimeSum(overtimeService.getOvertimeForMonth(userId, year, period));

        }

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
