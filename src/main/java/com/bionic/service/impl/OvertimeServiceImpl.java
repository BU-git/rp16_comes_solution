package com.bionic.service.impl;

import com.bionic.dao.ShiftDao;
import com.bionic.dto.OvertimeDTO;
import com.bionic.model.Shift;
import com.bionic.service.OvertimeService;
import com.bionic.service.WorkScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Forsent on 28.05.2016.
 */
@Service
public class OvertimeServiceImpl implements OvertimeService {

    @Autowired
    private ShiftDao shiftDao;

    @Autowired
    private WorkScheduleService workScheduleService;

    @Override
    public List<OvertimeDTO> getOvertimeForPeriod(int userId, int year, int period) {
        return null;
    }

    @Override
    public List<OvertimeDTO> getOvertimeForMonth(int userId, int year, int month) {
        return null;
    }

    @Override
    public OvertimeDTO getOvertimeForWeek(List<Shift> shifts, Date weekStartTime, Date weekEndTime, long contractTime) {
        return null;
    }
}
