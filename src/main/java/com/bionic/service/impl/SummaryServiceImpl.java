package com.bionic.service.impl;

import com.bionic.dao.ShiftDao;
import com.bionic.dao.UserDao;
import com.bionic.dto.WorkingWeekDTO;
import com.bionic.exception.shift.impl.ShiftsFromFuturePeriodException;
import com.bionic.exception.shift.impl.ShiftsNotFoundException;
import com.bionic.model.Ride;
import com.bionic.model.Shift;
import com.bionic.model.User;
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
    private UserDao userDao;

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

        int contractHours = 0;
        User user = userDao.findOne(userId);

        if (user.isZeroHours()) {
            contractHours = 40;
        } else {
            contractHours = workScheduleService.getContractHours(user.getWorkSchedule());
        }

        int contractTime = contractHours * 60 * 60 * 1000;
        List<WorkingWeekDTO> summary = new ArrayList<>();
        int numberOfWeeks = getWeeksBetween(monthStartTime, monthEndTime);

        for (int week = 1; week <= numberOfWeeks; week++) {
            Date weekStartTime = getMonthWeekStartTime(year, month, week);
            Date weekEndTime = getMonthWeekEndTime(year, month, week);
            int weekNumber = getMonthWeekOfYear(year, month, week);

            WorkingWeekDTO workingWeek = getSummaryForWeek(shifts, weekStartTime, weekEndTime, contractTime);
            workingWeek.setWeekNumber(weekNumber);
            System.out.println("worked time for week " + weekNumber + " = " + workingWeek.getWorkedTime());
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

        int contractHours = 0;
        User user = userDao.findOne(userId);

        if (user.isZeroHours()) {
            contractHours = 40;
        } else {
            contractHours = workScheduleService.getContractHours(user.getWorkSchedule());
        }

        int contractTime = contractHours * 60 * 60 * 1000;
        List<WorkingWeekDTO> summary = new ArrayList<>();

        for (int week = 1; week <= NUMBER_OF_WEEKS_IN_PERIOD; week++) {
            Date weekStartTime = getPeriodWeekStartTime(year, period, week);
            Date weekEndTime = getPeriodWeekEndTime(year, period, week);
            int weekNumber = getPeriodWeekOfYear(year, period, week);

            WorkingWeekDTO workingWeek = getSummaryForWeek(shifts, weekStartTime, weekEndTime, contractTime);
            workingWeek.setWeekNumber(weekNumber);
            System.out.println("worked time for week " + weekNumber + " = " + workingWeek.getWorkedTime());
            summary.add(workingWeek);
        }

        return summary;
    }

    public WorkingWeekDTO getSummaryForWeek(List<Shift> shifts, Date weekStartTime, Date weekEndTime, int contractTime) {

        WorkingWeekDTO workingWeek = new WorkingWeekDTO();
        Set<Shift> shiftSet = new HashSet<>();
        int workedTime = 0;
        //TODO add pause calculation for sequential rides
        int pauseTime = 0;
        Collections.sort(shifts, (l, r) -> (int)(l.getStartTime().getTime() - r.getStartTime().getTime()));

        shift:
        for (Shift s : shifts) {

            List<Ride> rides = s.getRides();
            Collections.sort(rides, (l, r) -> (int)(l.getStartTime().getTime() - r.getStartTime().getTime()));

            for (Ride r : rides) {
                if (r.getEndTime().getTime() >= weekStartTime.getTime()) {
                    if (r.getEndTime().getTime() > weekEndTime.getTime()) break shift;
                    long tempWorkedTime = r.getEndTime().getTime() - r.getStartTime().getTime();
                    workedTime += tempWorkedTime;
                    shiftSet.add(s);
                    System.out.println("temp worked time = " + tempWorkedTime);
                    if (r.equals(rides.get(0))) {
                        tempWorkedTime += r.getStartTime().getTime() - s.getStartTime().getTime();
                        System.out.println("temp worked time first = " + tempWorkedTime);
                        workedTime += r.getStartTime().getTime() - s.getStartTime().getTime();
                    }
                    if (r.equals(rides.get(rides.size()-1))) {
                        tempWorkedTime += s.getEndTime().getTime() - r.getEndTime().getTime();
                        System.out.println("temp worked time last = " + tempWorkedTime);
                        workedTime += s.getEndTime().getTime() - r.getEndTime().getTime();
                    }
                    int tempPauseTime = getPauseTime(tempWorkedTime);
                    pauseTime += tempPauseTime;
                }
            }
        }

        int overTime = 0;
        int actualWorkedTime = workedTime - pauseTime;
        if (actualWorkedTime >= contractTime) overTime = actualWorkedTime - contractTime;
        System.out.println("worked time = " + workedTime);
        System.out.println("pause time = " + pauseTime);
        System.out.println("actual worked time = " + actualWorkedTime);
        System.out.println("contract time = " + contractTime);
        workingWeek.setWorkedTime(actualWorkedTime);
        workingWeek.setOverTime(overTime);
        workingWeek.setShiftList(shiftSet);

        return workingWeek;
    }
}
