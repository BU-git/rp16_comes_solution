package com.bionic.service;

import com.bionic.model.DayType;

import java.util.Date;

public interface DayTypeService {
    DayType addDayType(DayType dayType);
    DayType editDayType(DayType dayType);
    DayType getDayType(int userId, Date day);
    void deleteDayType(DayType dayType);
}
