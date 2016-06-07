package com.bionic.service.impl;

import com.bionic.dao.ShiftDao;
import com.bionic.dto.WorkingWeekDTO;
import com.bionic.exception.shift.impl.ShiftsFromFuturePeriodException;
import com.bionic.exception.shift.impl.ShiftsNotFoundException;
import com.bionic.model.Ride;
import com.bionic.model.Shift;
import com.bionic.service.SummaryService;
import com.bionic.service.WorkScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.*;

import static com.bionic.service.util.MonthCalculator.getMonthEndTime;
import static com.bionic.service.util.MonthCalculator.getMonthStartTime;
import static com.bionic.service.util.PeriodCalculator.*;
import static com.bionic.service.util.WeekCalculator.*;

/**
 * @author Pavel Boiko
 */
@Service
public class SummaryServiceImpl implements SummaryService {

    @Autowired
    private ShiftDao shiftDao;

    @Autowired
    private WorkScheduleService workScheduleService;

    public List<WorkingWeekDTO> getSummaryForMonth(int userId, int year, int month)
                                    throws ShiftsNotFoundException, ShiftsFromFuturePeriodException {

        Date monthStartTime = getMonthStartTime(year, month);
        Date monthEndTime = getMonthEndTime(year, month);
        Date currentTime = Calendar.getInstance().getTime();

        if (monthStartTime.after(currentTime)) throw new ShiftsFromFuturePeriodException();

        List<Shift> shifts = shiftDao.getForPeriod(userId, monthStartTime, monthEndTime);
        if (ObjectUtils.isEmpty(shifts)) throw new ShiftsNotFoundException();

        List<WorkingWeekDTO> summary = new ArrayList<>();
        int numberOfWeeks = getWeeksBetween(monthStartTime, monthEndTime);

        for (int week = 1; week <= numberOfWeeks; week++) {
            Date weekStartTime = getMonthWeekStartTime(year, month, week);
            Date weekEndTime = getMonthWeekEndTime(year, month, week);
            int contractHours = 0;
            contractHours = workScheduleService.getContractHoursForWeek(userId, weekStartTime);
            if (contractHours == 0) contractHours = 40;
            long contractTime = contractHours * 60 * 60 * 1000;
            int weekNumber = getMonthWeekOfYear(year, month, week);

            WorkingWeekDTO workingWeek = getSummaryForWeek(shifts, weekStartTime, weekEndTime, contractTime);
            workingWeek.setWeekNumber(weekNumber);
            System.out.println("worked time for week " + weekNumber + " = " + (workingWeek.getWorkedTime() / 1000 / 60 / 60d));
            summary.add(workingWeek);
        }

        return summary;
    }

    public List<WorkingWeekDTO> getSummaryForPeriod(int userId, int year, int period)
                                    throws ShiftsNotFoundException, ShiftsFromFuturePeriodException {

        Date periodStartTime = getPeriodStartTime(year, period);
        Date periodEndTime = getPeriodEndTime(year, period);
        Date currentTime = Calendar.getInstance().getTime();

        if (periodStartTime.after(currentTime)) throw new ShiftsFromFuturePeriodException();

        List<Shift> shifts = shiftDao.getForPeriod(userId, periodStartTime, periodEndTime);
        if (ObjectUtils.isEmpty(shifts)) throw new ShiftsNotFoundException();

        List<WorkingWeekDTO> summary = new ArrayList<>();

        for (int week = 1; week <= NUMBER_OF_WEEKS_IN_PERIOD; week++) {
            Date weekStartTime = getPeriodWeekStartTime(year, period, week);
            Date weekEndTime = getPeriodWeekEndTime(year, period, week);
            int contractHours = 0;
            contractHours = workScheduleService.getContractHoursForWeek(userId, weekStartTime);
            if (contractHours == 0) contractHours = 40;
            long contractTime = contractHours * 60 * 60 * 1000;
            int weekNumber = getPeriodWeekOfYear(year, period, week);

            WorkingWeekDTO workingWeek = getSummaryForWeek(shifts, weekStartTime, weekEndTime, contractTime);
            workingWeek.setWeekNumber(weekNumber);
            System.out.println("worked time for week " + weekNumber + " = " + (workingWeek.getWorkedTime() / 1000 / 60 / 60d));
            summary.add(workingWeek);
        }

        return summary;
    }

    public WorkingWeekDTO getSummaryForWeek(List<Shift> shifts, Date weekStartTime, Date weekEndTime, long contractTime) {

        WorkingWeekDTO workingWeek = new WorkingWeekDTO();
        Set<Shift> shiftSet = new HashSet<>();
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
                if (r.getEndTime().getTime() > weekStartTime.getTime()) {
                    if (r.getStartTime().getTime() > weekEndTime.getTime()) break shift;
                    long tempWorkedTime = 0;
                    if (r.getStartTime().getTime() < weekStartTime.getTime()) {
                        tempWorkedTime = r.getEndTime().getTime() - weekStartTime.getTime();
                        workedTime += tempWorkedTime;
                        shiftSet.add(s);
                    } else if (r.getEndTime().getTime() > weekEndTime.getTime()) {
                        tempWorkedTime = weekEndTime.getTime() - r.getStartTime().getTime();
                        workedTime += tempWorkedTime;
                        shiftSet.add(s);
                    } else {
                        tempWorkedTime = r.getEndTime().getTime() - r.getStartTime().getTime();
                        workedTime += tempWorkedTime;
                        shiftSet.add(s);
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

        long overTime = 0;
        long actualWorkedTime = workedTime - pauseTime;
        if (actualWorkedTime >= contractTime) overTime = actualWorkedTime - contractTime;
        System.out.println("worked time = " + (workedTime / 1000 / 60 / 60d));
        System.out.println("pause time = " + (pauseTime / 1000 / 60 / 60d));
        System.out.println("actual worked time = " + (actualWorkedTime / 1000 / 60 / 60d));
        System.out.println("contract time = " + (contractTime / 1000 / 60 / 60d));
        workingWeek.setWorkedTime(actualWorkedTime);
        workingWeek.setOverTime(overTime);
        workingWeek.setShiftList(shiftSet);

        return workingWeek;
    }
}
