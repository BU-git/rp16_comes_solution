package com.bionic.service.impl;

import com.bionic.dao.DayTypeDao;
import com.bionic.model.DayType;
import com.bionic.model.User;
import com.bionic.service.DayTypeService;
import com.bionic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DayTypeServiceImpl implements DayTypeService {

    @Autowired
    private DayTypeDao dayTypeDao;

    @Autowired
    private UserService userService;

    @Override
    public DayType addDayType(DayType dayType) {
        User user = userService.getAuthUser();
        dayType.setUser(user);
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
