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

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.bionic.service.util.DayCalculator.getDayEndTime;
import static com.bionic.service.util.DayCalculator.getDayStartTime;
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
    public List<OvertimeDTO> getOvertimeForPeriod(int userId, int year, int period) throws ShiftsNotFoundException {

        Date periodStartTime = getPeriodStartTime(year, period);
        Date periodEndTime = getPeriodEndTime(year, period);

        List<Shift> shifts = shiftDao.getForPeriod(userId, periodStartTime, periodEndTime);
        if (ObjectUtils.isEmpty(shifts)) throw new ShiftsNotFoundException();
        List<DayType> dayTypes = dayTypeDao.getDayTypesForPeriod(userId, periodStartTime, periodEndTime);


        List<OvertimeDTO> overtime = new ArrayList<>();

        for (int week = 1; week <= NUMBER_OF_WEEKS_IN_PERIOD; week++) {
            Date weekStartTime = getPeriodWeekStartTime(year, period, week);
            Date weekEndTime = getPeriodWeekEndTime(year, period, week);
            int contractHours = 0;
            contractHours = workScheduleService.getContractHoursForWeek(userId, weekStartTime);
            if (contractHours == 0) contractHours = 40;
            long contractTime = contractHours * 60 * 60 * 1000;
            int weekNumber = getPeriodWeekOfYear(year, period, week);

            OvertimeDTO overtimeDTO = getOvertimeForWeek(dayTypes, shifts, weekStartTime, weekEndTime, contractTime);
            overtimeDTO.setWeekOfYear(weekNumber);
            overtimeDTO.setStartTime(weekStartTime);
            overtimeDTO.setEndTime(weekEndTime);

        }

        return overtime;
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

            int contractHours = workScheduleService.getContractHoursForWeek(userId, weekStartTime);
            if (contractHours == 0) contractHours = 40;
//            long contractTime = contractHours * 60 * 60 * 1000;
            int weekNumber = getMonthWeekOfYear(year, month, week);

            List<DayType> dayTypes = dayTypeDao.getDayTypesForPeriod(userId, monthStartTime, monthEndTime);

            OvertimeDTO overtimeDTO = getOvertimeForWeek(dayTypes, shifts, weekStartTime, weekEndTime, contractHours);
            overtimeDTO.setWeekOfYear(weekNumber);
            overtimeDTO.setStartTime(weekStartTime);
            overtimeDTO.setEndTime(weekEndTime);

            overtimeDTOs.add(overtimeDTO);
        }

        return overtimeDTOs;
    }

    @Override
    public OvertimeDTO getOvertimeForWeek(List<DayType> dayTypes, List<Shift> shifts, Date weekStartTime, Date weekEndTime, long contractHours) {
        OvertimeDTO overtimeDTO = new OvertimeDTO();


        for (int day = 1; day <= 7; day++) {

            Date dayStartTime = getDayStartTime(weekStartTime, day);
            Date dayEndTime = getDayEndTime(weekEndTime, day);

            overtimeDTO = getOvertimeForDay(overtimeDTO, dayTypes, shifts, dayStartTime, dayEndTime, contractHours);
            overtimeDTO = fillByDayTypes(overtimeDTO, dayTypes, weekStartTime, weekEndTime);
        }

        return overtimeDTO;
    }

    private OvertimeDTO fillByDayTypes(OvertimeDTO overtimeDTO, List<DayType> dayTypes, Date weekStartTime, Date weekEndTime) {
        final int standartHours = 8;
        for(DayType dayType: dayTypes) {
            if (dayType.getStartTime().after(weekEndTime) && dayType.getEndTime().before(weekStartTime)) continue;

            LocalDateTime dayTypeStartTime = LocalDateTime.ofInstant(dayType.getStartTime().toInstant(), ZoneId.systemDefault());
            LocalDateTime dayTypeEndTime = LocalDateTime.ofInstant(dayType.getEndTime().toInstant(), ZoneId.systemDefault());
            double workedHours = dayTypeEndTime.getHour() - dayTypeStartTime.getHour() + 100 * (dayTypeEndTime.getMinute() - dayTypeStartTime.getMinute()) / 60 / 100;

            switch (dayType.getDayTypeName()) {
                case WAITING_DAY:
                    double waitingDayHours = overtimeDTO.getWaitingdayHours();
                    waitingDayHours += standartHours;
                    overtimeDTO.setWaitingdayHours(waitingDayHours);
                    break;
                case SICK_DAY:
                    double sickDayHours = overtimeDTO.getSickdayHours();
                    sickDayHours += standartHours;
                    overtimeDTO.setSickdayHours(sickDayHours);
                    break;
                case HOLIDAY:
                    double holidayHours = overtimeDTO.getHolidayHours();
                    holidayHours += standartHours;
                    overtimeDTO.setHolidayHours(holidayHours);
                    break;
                case ATV_DAY:
                    double AtvHours = overtimeDTO.getAtvHours();
                    AtvHours += standartHours;
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
        return overtimeDTO;
    }

    private OvertimeDTO getOvertimeForDay(OvertimeDTO overtimeDTO, List<DayType> dayTypes, List<Shift> shifts,
                                          Date dayStartTime, Date dayEndTime, long contractHours) {


        shift:
        for (Shift s : shifts) {

            List<Ride> rides = s.getRides();
            Collections.sort(rides, (l, r) -> (int) (l.getStartTime().getTime() - r.getStartTime().getTime()));

            for (Ride r : rides) {
                if (r.getEndTime().getTime() > dayStartTime.getTime()) {
                    if (r.getStartTime().getTime() > dayEndTime.getTime()) break shift;
                    //for valid rides
                    LocalDateTime rideStartTime = LocalDateTime.ofInstant(r.getStartTime().toInstant(), ZoneId.systemDefault());
                    LocalDateTime rideEndTime = LocalDateTime.ofInstant(r.getEndTime().toInstant(), ZoneId.systemDefault());

                    if (rideStartTime.getDayOfMonth() == rideEndTime.getDayOfMonth()) {
                        overtimeDTO = checkIfWeekend(overtimeDTO, rideStartTime, rideEndTime, contractHours);
                    } else {
                        Date rideMiddleDate = getDayEndTime(r.getStartTime());
                        LocalDateTime rideMiddleTime = LocalDateTime.ofInstant(rideMiddleDate.toInstant(), ZoneId.systemDefault());
                        overtimeDTO = checkIfWeekend(overtimeDTO, rideStartTime, rideMiddleTime, contractHours);
                        overtimeDTO = checkIfWeekend(overtimeDTO, rideMiddleTime.plusSeconds(1), rideEndTime, contractHours);
                    }

                }
            }
        }
        return overtimeDTO;
    }

    private OvertimeDTO checkIfWeekend(OvertimeDTO overtimeDTO, LocalDateTime rideStartTime, LocalDateTime rideEndTime, long contractHours) {
        //hours in format like 10.75 or 2.4 etc.
        double workedHours = rideEndTime.getHour() - rideStartTime.getHour() + 100 * (rideEndTime.getMinute() - rideStartTime.getMinute()) / 60 / 100;

        switch (rideStartTime.getDayOfWeek()) {
            case SATURDAY:
                double sat = overtimeDTO.getPaid150();
                sat += workedHours;
                overtimeDTO.setPaid150(sat);
                break;
            case SUNDAY:
                double sun = overtimeDTO.getPaid200();
                sun += workedHours;
                overtimeDTO.setPaid200(sun);
                break;
            default:
                double paid100 = overtimeDTO.getPaid100();
                if (paid100 >= contractHours) {
                    double overtime = overtimeDTO.getPaid130();
                    overtime += workedHours;
                    overtimeDTO.setPaid130(overtime);
                } else if ((paid100 + workedHours) > contractHours){
                    overtimeDTO.setPaid100(contractHours);
                    double overtime = paid100 + workedHours - contractHours;
                    overtimeDTO.setPaid130(overtime);
                }
                break;
        }
        return overtimeDTO;
    }
}
