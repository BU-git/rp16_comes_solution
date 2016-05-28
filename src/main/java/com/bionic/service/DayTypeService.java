package com.bionic.service;

import com.bionic.model.DayType;

import java.util.Date;
import java.util.List;

public interface DayTypeService {
    DayType addDayType(DayType dayType);
    DayType editDayType(DayType dayType);
    DayType getDayType(int userId, Date day);
    void deleteDayType(DayType dayType);
    List<DayType> getDayTypesForPeriod(int user_id, Date startDate, Date endDate);
}
