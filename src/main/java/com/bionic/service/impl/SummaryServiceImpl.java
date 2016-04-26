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

import java.util.*;

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

    public List<WorkingWeekDTO> getSummary(int userId, int year, int period)
                                    throws ShiftsNotFoundException, ShiftsFromFuturePeriodException {

        Date periodStartTime = getPeriodStartTime(year, period);
        Date periodEndTime = getPeriodEndTime(year, period);
        Date currentTime = Calendar.getInstance().getTime();

        if (periodStartTime.after(currentTime)) throw new ShiftsFromFuturePeriodException();

        List<Shift> shifts = shiftDao.getForPeriod(userId, periodStartTime, periodEndTime);
        if (shifts == null) throw new ShiftsNotFoundException();

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
            Date weekStartTime = getWeekStartTime(year, period, week);
            Date weekEndTime = getWeekEndTime(year, period, week);
            workingWeek.setWeekNumber(getWeekOfYear(year, period, week));
            workingWeek.setContractTime(contractTime);
            Set<Integer> shiftIdList = new HashSet<>();

            Collections.sort(shifts, (l, r) -> (int)(l.getStartTime().getTime() - r.getStartTime().getTime()));
            int workingTime = 0;

            shift:
            for (Shift s : shifts) {

                List<Ride> rides = s.getRides();
                Collections.sort(rides, (l, r) -> (int)(l.getStartTime().getTime() - r.getStartTime().getTime()));

                for (Ride r : rides) {
                    if (r.getEndTime().getTime() >= weekStartTime.getTime()) {
                        if (r.getEndTime().getTime() > weekEndTime.getTime()) break shift;
                        workingTime += r.getEndTime().getTime() - r.getStartTime().getTime();
                        shiftIdList.add(s.getId());
                        if (r.equals(rides.get(0))) workingTime += r.getStartTime().getTime() - s.getStartTime().getTime();
                        if (r.equals(rides.get(rides.size()-1))) workingTime += s.getEndTime().getTime() - r.getEndTime().getTime();
                    }
                }
            }
            System.out.println("working time for week " + week + " = " + workingTime);
            int overTime = 0;
            if (workingTime >= contractTime) overTime = workingTime - contractTime;
            workingWeek.setOverTime(overTime);
            workingWeek.setShiftIdList(shiftIdList);
            summary.add(workingWeek);
        }

        return summary;
    }
}
