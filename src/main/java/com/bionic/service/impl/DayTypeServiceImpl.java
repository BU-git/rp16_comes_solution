package com.bionic.service.impl;

import com.bionic.dao.DayTypeDao;
import com.bionic.model.DayType;
import com.bionic.service.DayTypeService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

public class DayTypeServiceImpl implements DayTypeService {

    @Autowired
    private DayTypeDao dayTypeDao;

    @Override
    public DayType addDayType(DayType dayType) {
        return dayTypeDao.saveAndFlush(dayType);
    }

    @Override
    public DayType editDayType(DayType dayType) {
        return dayTypeDao.saveAndFlush(dayType);
    }

    @Override
    public DayType getDayType(int userId, Date day) {
        return dayTypeDao.getDayType(userId, day);
    }

    @Override
    public void deleteDayType(DayType dayType) {
        dayTypeDao.delete(dayType.getId());
    }

    @Override
    public List<DayType> getDayTypesForPeriod(int user_id, Date startDate, Date endDate) {
        return dayTypeDao.getDayTypesForPeriod(user_id, startDate, endDate);
    }
}
