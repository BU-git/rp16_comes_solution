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
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        if (shifts == null || shifts.size() == 0) throw new ShiftsNotFoundException();

        int contractHours = 0;
        User user = userDao.findOne(userId);

        if (user.isZeroHours() == true) {
            contractHours = 40;
        } else {
            contractHours = workScheduleService.getContractHours(user.getWorkSchedule());
        }

        int contractTime = contractHours * 60 * 60 * 1000;
        List<WorkingWeekDTO> summary = new ArrayList<>();
        int numberOfWeeks = getWeeksBetween(monthStartTime, monthEndTime);

        for (int week = 1; week <= numberOfWeeks; week++) {
            WorkingWeekDTO workingWeek = new WorkingWeekDTO();
            Date weekStartTime = getMonthWeekStartTime(year, month, week);
            Date weekEndTime = getMonthWeekEndTime(year, month, week);
            workingWeek.setWeekNumber(getMonthWeekOfYear(year, month, week));

            Pair<Integer, Set<Shift>> weekSummary = getSummaryForWeek(shifts, weekStartTime, weekEndTime);
            int workedTime = weekSummary.getKey();
            Set<Shift> shiftSet = weekSummary.getValue();
            System.out.println("worked time for week " + week + " = " + workedTime);
            int overTime = 0;
            if (workedTime >= contractTime) overTime = workedTime - contractTime;
            workingWeek.setWorkedTime(workedTime);
            workingWeek.setOverTime(overTime);
            workingWeek.setShiftList(shiftSet);
            summary.add(workingWeek);
        }

        return summary;
    }

    public Pair<Integer, Set<Shift>> getSummaryForWeek(List<Shift> shifts, Date weekStartTime, Date weekEndTime) {
        Set<Shift> shiftList = new HashSet<>();
        int workingTime = 0;
        Collections.sort(shifts, (l, r) -> (int)(l.getStartTime().getTime() - r.getStartTime().getTime()));

        shift:
        for (Shift s : shifts) {

            List<Ride> rides = s.getRides();
            Collections.sort(rides, (l, r) -> (int)(l.getStartTime().getTime() - r.getStartTime().getTime()));

            for (Ride r : rides) {
                if (r.getEndTime().getTime() >= weekStartTime.getTime()) {
                    if (r.getEndTime().getTime() > weekEndTime.getTime()) break shift;
                    workingTime += r.getEndTime().getTime() - r.getStartTime().getTime();
                    shiftList.add(s);
                    if (r.equals(rides.get(0))) workingTime += r.getStartTime().getTime() - s.getStartTime().getTime();
                    if (r.equals(rides.get(rides.size()-1))) workingTime += s.getEndTime().getTime() - r.getEndTime().getTime();
                }
            }
        }
        return new Pair<>(workingTime, shiftList);
    }

    public List<WorkingWeekDTO> getSummaryForPeriod(int userId, int year, int period)
                                    throws ShiftsNotFoundException, ShiftsFromFuturePeriodException {

        Date periodStartTime = getPeriodStartTime(year, period);
        Date periodEndTime = getPeriodEndTime(year, period);
        Date currentTime = Calendar.getInstance().getTime();

        if (periodStartTime.after(currentTime)) throw new ShiftsFromFuturePeriodException();

        List<Shift> shifts = shiftDao.getForPeriod(userId, periodStartTime, periodEndTime);
        if (shifts == null || shifts.size() == 0) throw new ShiftsNotFoundException();

        int contractHours = 0;
        User user = userDao.findOne(userId);

        if (user.isZeroHours() == true) {
            contractHours = 40;
        } else {
            contractHours = workScheduleService.getContractHours(user.getWorkSchedule());
        }

        int contractTime = contractHours * 60 * 60 * 1000;
        List<WorkingWeekDTO> summary = new ArrayList<>();

        for (int week = 1; week <= NUMBER_OF_WEEKS_IN_PERIOD; week++) {
            WorkingWeekDTO workingWeek = new WorkingWeekDTO();
            Date weekStartTime = getPeriodWeekStartTime(year, period, week);
            Date weekEndTime = getPeriodWeekEndTime(year, period, week);
            workingWeek.setWeekNumber(getPeriodWeekOfYear(year, period, week));

            Pair<Integer, Set<Shift>> weekSummary = getSummaryForWeek(shifts, weekStartTime, weekEndTime);
            int workedTime = weekSummary.getKey();
            Set<Shift> shiftSet = weekSummary.getValue();
            System.out.println("worked time for week " + week + " = " + workedTime);
            int overTime = 0;
            if (workedTime >= contractTime) overTime = workedTime - contractTime;
            workingWeek.setWorkedTime(workedTime);
            workingWeek.setOverTime(overTime);
            workingWeek.setShiftList(shiftSet);
            summary.add(workingWeek);

        }

        return summary;
    }


}
