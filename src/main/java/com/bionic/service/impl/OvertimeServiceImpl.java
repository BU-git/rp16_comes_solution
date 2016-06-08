package com.bionic.service.impl;

import com.bionic.dao.DayTypeDao;
import com.bionic.dao.ShiftDao;
import com.bionic.dto.OvertimeDTO;
import com.bionic.exception.shift.impl.ShiftsNotFoundException;
import com.bionic.model.DayType;
import com.bionic.model.Ride;
import com.bionic.model.Shift;
import com.bionic.service.OvertimeService;
import com.bionic.service.WorkScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.bionic.service.util.DayCalculator.isNationalHoliday;
import static com.bionic.service.util.MonthCalculator.getMonthEndTime;
import static com.bionic.service.util.MonthCalculator.getMonthStartTime;
import static com.bionic.service.util.PeriodCalculator.*;
import static com.bionic.service.util.WeekCalculator.*;

/**
 * Created by Forsent on 28.05.2016.
 */
@Service
public class OvertimeServiceImpl implements OvertimeService {

    @Autowired
    private ShiftDao shiftDao;

    @Autowired
    private DayTypeDao dayTypeDao;

    @Autowired
    private WorkScheduleService workScheduleService;


    @Override
    public OvertimeDTO getOvertimeSum(List<OvertimeDTO> overtimeDTOList) {
        OvertimeDTO overtimeSum = new OvertimeDTO();

        for (OvertimeDTO o : overtimeDTOList) {
            overtimeSum.setTotalHours(overtimeSum.getTotalHours() + o.getTotalHours());
            overtimeSum.setPaid100(overtimeSum.getPaid100() + o.getPaid100());
            overtimeSum.setPaid130(overtimeSum.getPaid130() + o.getPaid130());
            overtimeSum.setPaid150(overtimeSum.getPaid150() + o.getPaid150());
            overtimeSum.setPaid200(overtimeSum.getPaid200() + o.getPaid200());
            overtimeSum.setWaitingdayHours(overtimeSum.getWaitingdayHours() + o.getWaitingdayHours());
            overtimeSum.setSickdayHours(overtimeSum.getSickdayHours() + o.getSickdayHours());
            overtimeSum.setHolidayHours(overtimeSum.getHolidayHours() + o.getHolidayHours());
            overtimeSum.setAtvHours(overtimeSum.getAtvHours() + o.getAtvHours());
            overtimeSum.setPaidLeaveHours(overtimeSum.getPaidLeaveHours() + o.getPaidLeaveHours());
            overtimeSum.setUnpaidLeaveHours(overtimeSum.getUnpaidLeaveHours() + o.getUnpaidLeaveHours());
        }

        return overtimeSum;
    }

    @Override
    public List<OvertimeDTO> getOvertimeForPeriod(int userId, int year, int period) throws ShiftsNotFoundException {

        Date periodStartTime = getPeriodStartTime(year, period);
        Date periodEndTime = getPeriodEndTime(year, period);

        List<Shift> shifts = shiftDao.getForPeriod(userId, periodStartTime, periodEndTime);
        if (ObjectUtils.isEmpty(shifts)) throw new ShiftsNotFoundException();
        List<DayType> dayTypes = dayTypeDao.getDayTypesForPeriod(userId, periodStartTime, periodEndTime);


        List<OvertimeDTO> overtimeDTOs = new ArrayList<>();

        for (int week = 1; week <= NUMBER_OF_WEEKS_IN_PERIOD; week++) {
            Date weekStartTime = getPeriodWeekStartTime(year, period, week);
            Date weekEndTime = getPeriodWeekEndTime(year, period, week);
            int contractHours = 0;
            contractHours = workScheduleService.getContractHoursForWorkingWeek(userId, weekStartTime);
            if (contractHours == 0) contractHours = 40;
            long contractTime = contractHours * 60 * 60 * 1000;
            int weekNumber = getPeriodWeekOfYear(year, period, week);

            OvertimeDTO overtimeDTO = getOvertimeForWeek(dayTypes, shifts, weekStartTime, weekEndTime, contractTime);
            overtimeDTO.setWeekOfYear(weekNumber);
            overtimeDTOs.add(overtimeDTO);
        }

        return overtimeDTOs;
    }

    @Override
    public List<OvertimeDTO> getOvertimeForMonth(int userId, int year, int month) throws ShiftsNotFoundException {
        Date monthStartTime = getMonthStartTime(year, month);
        Date monthEndTime = getMonthEndTime(year, month);


        List<Shift> shifts = shiftDao.getForPeriod(userId, monthStartTime, monthEndTime);
        if (ObjectUtils.isEmpty(shifts)) throw new ShiftsNotFoundException();

        List<OvertimeDTO> overtimeDTOs = new ArrayList<>();
        int numberOfWeeks = getWeeksBetween(monthStartTime, monthEndTime);

        for (int week = 1; week <= numberOfWeeks; week++) {
            Date weekStartTime = getMonthWeekStartTime(year, month, week);
            Date weekEndTime = getMonthWeekEndTime(year, month, week);

            int contractHours = workScheduleService.getContractHoursForWorkingWeek(userId, weekStartTime);
            if (contractHours == 0) contractHours = 40;
            long contractTime = contractHours * 60 * 60 * 1000;
            int weekNumber = getMonthWeekOfYear(year, month, week);

            List<DayType> dayTypes = dayTypeDao.getDayTypesForPeriod(userId, monthStartTime, monthEndTime);

            OvertimeDTO overtimeDTO = getOvertimeForWeek(dayTypes, shifts, weekStartTime, weekEndTime, contractTime);
            overtimeDTO.setWeekOfYear(weekNumber);
            overtimeDTOs.add(overtimeDTO);
        }

        return overtimeDTOs;
    }

    @Override
    public OvertimeDTO getOvertimeForWeek(List<DayType> dayTypes, List<Shift> shifts, Date weekStartTime, Date weekEndTime, long contractTime) {

        OvertimeDTO overtimeDTO = new OvertimeDTO();

        Date workingWeekEndTime = getWorkingWeekEndTime(weekEndTime);
        Date saturdayStartTime = getSaturdayStartTime(weekStartTime);
        Date saturdayEndTime = getSaturdayEndTime(weekEndTime);
        Date sundayStartTime = getSundayStartTime(weekStartTime);

        long actualWorkedTime = getWorkedTimeForPeriod(shifts, weekStartTime, workingWeekEndTime);
        long overTime = 0;
        long paid100Time = actualWorkedTime;
        if (actualWorkedTime > contractTime) {
            overTime = actualWorkedTime - contractTime;
            paid100Time = contractTime;
        }
        long saturdayWorkedTime = getWorkedTimeForPeriod(shifts, saturdayStartTime, saturdayEndTime);
        long sundayWorkedTime = getWorkedTimeForPeriod(shifts, sundayStartTime, weekEndTime);
        long totalTime = actualWorkedTime + saturdayWorkedTime + sundayWorkedTime;

        overtimeDTO.setTotalHours(totalTime / MILLIS_IN_HOUR);
        overtimeDTO.setPaid100(paid100Time / MILLIS_IN_HOUR);
        overtimeDTO.setPaid130(overTime / MILLIS_IN_HOUR);

        if (isNationalHoliday(saturdayStartTime)) {
            overtimeDTO.setPaid200((saturdayWorkedTime + sundayWorkedTime) / MILLIS_IN_HOUR);
        } else {
            overtimeDTO.setPaid150(saturdayWorkedTime / MILLIS_IN_HOUR);
            overtimeDTO.setPaid200(sundayWorkedTime / MILLIS_IN_HOUR);
        }


        overtimeDTO = fillByDayTypes(overtimeDTO, dayTypes, weekStartTime, weekEndTime);

        overtimeDTO.setStartTime(weekStartTime);
        overtimeDTO.setEndTime(weekEndTime);

        return overtimeDTO;

    }

    @Override
    public long getWorkedTimeForPeriod(List<Shift> shifts, Date startTime, Date endTime) {

        int workedTime = 0;
        int pauseTime = 0;
        Collections.sort(shifts, (l, r) -> (int)(l.getStartTime().getTime() - r.getStartTime().getTime()));

        shift:
        for (Shift s : shifts) {

            List<Ride> rides = s.getRides();
            Collections.sort(rides, (l, r) -> (int)(l.getStartTime().getTime() - r.getStartTime().getTime()));

            int sequentialWorkedTime = 0;

            for (int i = 0; i < rides.size(); i++) {
                Ride r = rides.get(i);
                if (r.getEndTime().getTime() > startTime.getTime()) {
                    if (r.getStartTime().getTime() > endTime.getTime()) break shift;
                    long tempWorkedTime;


                    if (r.getStartTime().getTime() < startTime.getTime()) {
                        tempWorkedTime = r.getEndTime().getTime() - startTime.getTime();
                        workedTime += tempWorkedTime;
                    } else if (r.getEndTime().getTime() > endTime.getTime()) {
                        tempWorkedTime = endTime.getTime() - r.getStartTime().getTime();
                        workedTime += tempWorkedTime;
                    } else {
                        tempWorkedTime = r.getEndTime().getTime() - r.getStartTime().getTime();
                        workedTime += tempWorkedTime;
                    }

                    System.out.println("temp worked time = " + (tempWorkedTime/ 1000 / 60 / 60d));
                    if (i == 0) {
                        tempWorkedTime += r.getStartTime().getTime() - s.getStartTime().getTime();
                        System.out.println("temp worked time first = " + (tempWorkedTime / 1000 / 60 / 60d));
                        workedTime += r.getStartTime().getTime() - s.getStartTime().getTime();
                    }
                    if (i == rides.size()-1) {
                        tempWorkedTime += s.getEndTime().getTime() - r.getEndTime().getTime();
                        System.out.println("temp worked time last = " + (tempWorkedTime / 1000 / 60 / 60d));
                        workedTime += s.getEndTime().getTime() - r.getEndTime().getTime();
                    }

                    //Calculate pause for sequential time rides
                    if (i == 0 && i != rides.size()-1) {
                        if (rides.get(i).getEndTime().getTime() == rides.get(i + 1).getStartTime().getTime()){
                            sequentialWorkedTime += tempWorkedTime;
                        } else {
                            long tempPauseTime = getPauseTime(tempWorkedTime);
                            pauseTime += tempPauseTime;
                        }

                    } else if (i != 0 && i != rides.size()-1) {
                        if (rides.get(i).getEndTime().getTime() == rides.get(i + 1).getStartTime().getTime()){
                            sequentialWorkedTime += tempWorkedTime;
                        } else if (sequentialWorkedTime > 0) {
                            sequentialWorkedTime += tempWorkedTime;
                            long tempPauseTime = getPauseTime(sequentialWorkedTime);
                            System.out.println("sequential worked time = " + (sequentialWorkedTime / 1000 / 60 / 60d));
                            pauseTime += tempPauseTime;
                            sequentialWorkedTime = 0;
                        } else {
                            long tempPauseTime = getPauseTime(tempWorkedTime);
                            pauseTime += tempPauseTime;
                        }

                    } else if (i == rides.size()-1) {
                        if (sequentialWorkedTime > 0) {
                            sequentialWorkedTime += tempWorkedTime;
                            long tempPauseTime = getPauseTime(sequentialWorkedTime);
                            System.out.println("sequential worked time = " + (sequentialWorkedTime / 1000 / 60 / 60d));
                            pauseTime += tempPauseTime;
                            sequentialWorkedTime = 0;
                        } else {
                            long tempPauseTime = getPauseTime(tempWorkedTime);
                            pauseTime += tempPauseTime;
                        }
                    }

                }
            }
        }

        long actualWorkedTime = workedTime - pauseTime;
        return actualWorkedTime;
    }

    private OvertimeDTO fillByDayTypes(OvertimeDTO overtimeDTO, List<DayType> dayTypes, Date weekStartTime, Date weekEndTime) {
        final int standardHours = 8;
        for(DayType dayType: dayTypes) {
            if (dayType.getStartTime().after(weekEndTime) || dayType.getEndTime().before(weekStartTime)) continue;

            double workedHours = (dayType.getEndTime().getTime() - dayType.getStartTime().getTime()) / 1000 / 60 / 60;

            switch (dayType.getDayTypeName()) {
                case WAITING_DAY:
                    double waitingDayHours = overtimeDTO.getWaitingdayHours();
                    waitingDayHours += standardHours;
                    overtimeDTO.setWaitingdayHours(waitingDayHours);
                    break;
                case SICK_DAY:
                    double sickDayHours = overtimeDTO.getSickdayHours();
                    sickDayHours += standardHours;
                    overtimeDTO.setSickdayHours(sickDayHours);
                    break;
                case HOLIDAY:
                    double holidayHours = overtimeDTO.getHolidayHours();
                    holidayHours += standardHours;
                    overtimeDTO.setHolidayHours(holidayHours);
                    break;
                case ATV_DAY:
                    double AtvHours = overtimeDTO.getAtvHours();
                    AtvHours += standardHours;
                    overtimeDTO.setAtvHours(AtvHours);
                    break;
                case PAID_LEAVE_OF_ABSENCE:
                    double paidLeaveHours = overtimeDTO.getPaidLeaveHours();
                    paidLeaveHours += workedHours;
                    overtimeDTO.setPaidLeaveHours(paidLeaveHours);
                    break;
                case UNPAID_LEAVE_OF_ABSENCE:
                    double unpaidLeaveHours = overtimeDTO.getUnpaidLeaveHours();
                    unpaidLeaveHours += workedHours;
                    overtimeDTO.setUnpaidLeaveHours(unpaidLeaveHours);
                    break;
            }
        }
        double total = overtimeDTO.getPaid100() + overtimeDTO.getPaid130() + overtimeDTO.getPaid150() + overtimeDTO.getPaid200();
        overtimeDTO.setTotalHours(total);
        return overtimeDTO;
    }

}
