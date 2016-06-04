package com.bionic.service;

import com.bionic.dto.OvertimeDTO;
import com.bionic.exception.shift.impl.ShiftsNotFoundException;
import com.bionic.model.DayType;
import com.bionic.model.Shift;

import java.util.Date;
import java.util.List;

/**
 * Created by Forsent on 28.05.2016.
 */
public interface OvertimeService {

    OvertimeDTO getOvertimeSum(List<OvertimeDTO> overtimeDTOList);

    List<OvertimeDTO> getOvertimeForPeriod(int userId, int year, int period) throws ShiftsNotFoundException;

    List<OvertimeDTO> getOvertimeForMonth(int userId, int year, int month) throws ShiftsNotFoundException;

    OvertimeDTO getOvertimeForWeek(OvertimeDTO overtimeDTO, List<DayType> dayTypes, List<Shift> shifts, Date weekStartTime, Date weekEndTime, long contractTime);

}
