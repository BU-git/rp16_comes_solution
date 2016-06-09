package com.bionic.service;

import com.bionic.dto.OvertimeDTO;
import com.bionic.model.DayType;
import com.bionic.model.Shift;

import java.util.Date;
import java.util.List;

/**
 * Created by Forsent on 28.05.2016.
 */
public interface OvertimeService {

    OvertimeDTO getOvertimeSum(List<OvertimeDTO> overtimeDTOList);

    List<OvertimeDTO> getOvertimeForPeriod(int userId, int year, int period);

    List<OvertimeDTO> getOvertimeForMonth(int userId, int year, int month);

    long getWorkedTimeForPeriod(List<Shift> shifts, Date startTime, Date endTime);

    OvertimeDTO getOvertimeForWeek(List<DayType> dayTypes, List<Shift> shifts, Date weekStartTime, Date weekEndTime, long contractTime);

}
