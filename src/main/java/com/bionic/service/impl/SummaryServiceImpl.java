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

        Date startTime = getPeriodStartTime(year, period);
        Date endTime = getPeriodEndTime(year, period);
        Date currentTime = Calendar.getInstance().getTime();

        if (startTime.after(currentTime)) throw new ShiftsFromFuturePeriodException();

        List<Shift> shifts = shiftDao.getForPeriod(userId, startTime, endTime);
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
            workingWeek.setWeekNumber(getWeekOfYear(year, period, week));
            workingWeek.setContractTime(contractTime);

            Collections.sort(shifts, (l, r) -> (int)(l.getStartTime().getTime() - r.getStartTime().getTime()));

            for (Shift s : shifts) {

                List<Ride> rides = s.getRides();
                Collections.sort(rides, (l, r) -> (int)(l.getStartTime().getTime() - r.getStartTime().getTime()));

                for (Ride r : rides) {


                }
            }
        }

        return null;
    }
}
