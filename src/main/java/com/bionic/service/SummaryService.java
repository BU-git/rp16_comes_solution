package com.bionic.service;

import com.bionic.dto.WorkingWeekDTO;
import com.bionic.exception.shift.impl.ShiftsFromFuturePeriodException;
import com.bionic.exception.shift.impl.ShiftsNotFoundException;
import com.bionic.model.Shift;

import java.util.Date;
import java.util.List;

/**
 * @author Pavel Boiko
 */
public interface SummaryService {

    List<WorkingWeekDTO> getSummaryForPeriod(int userId, int year, int period)
                                throws ShiftsNotFoundException, ShiftsFromFuturePeriodException;

    List<WorkingWeekDTO> getSummaryForMonth(int userId, int year, int month)
            throws ShiftsNotFoundException, ShiftsFromFuturePeriodException;

    WorkingWeekDTO getSummaryForWeek(List<Shift> shifts, Date weekStartTime, Date weekEndTime, int contractTime);
}
