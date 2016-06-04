package com.bionic.service.impl;

import com.bionic.dao.DayTypeDao;
import com.bionic.model.DayType;
import com.bionic.model.User;
import com.bionic.model.dict.DayTypeEnum;
import com.bionic.service.DayTypeService;
import com.bionic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
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
        if (dayType.getDayTypeName().equals(DayTypeEnum.SICK_DAY)) {
            LocalDateTime dayTypeStartDay = LocalDateTime.ofInstant(dayType.getStartTime().toInstant(), ZoneId.systemDefault());
            LocalDateTime fourWeeksAgo = dayTypeStartDay.minusWeeks(4);
            Date fourWeekAgoDate = Date.from(fourWeeksAgo.atZone(ZoneId.systemDefault()).toInstant());

            List<DayType> waitingDays = dayTypeDao.getWainigDayForPeriod(dayType.getUser().getId(), fourWeekAgoDate, dayType.getStartTime());
            if (ObjectUtils.isEmpty(waitingDays)) dayType.setDayTypeName(DayTypeEnum.WAITING_DAY);
        }
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
