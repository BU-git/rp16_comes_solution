package com.bionic.service.impl;

import com.bionic.config.RootConfig;
import com.bionic.config.WebConfig;
import com.bionic.controllers.report.ReportDTO;
import com.bionic.dao.ShiftDao;
import com.bionic.model.Ride;
import com.bionic.model.Shift;
import com.bionic.model.User;
import com.bionic.service.ReportService;
import com.bionic.service.UserService;
import com.bionic.service.util.PeriodCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Dima Budko
 */

@Service
public class ReportServiceImpl implements ReportService {



    @Autowired
    private ShiftDao shiftDao;


    @Override
    public List<ReportDTO> getReportList(User user, int year, int period) {
        List<ReportDTO> reportList = new ArrayList<>();
        Date periodStartTime =  PeriodCalculator.getPeriodStartTime(year,period);
        Date periodEndTime = PeriodCalculator.getPeriodEndTime(year,period);
        List<Shift> shifts = shiftDao.getForPeriod(user.getId(),periodStartTime,periodEndTime);
        int i = 0;
        for (Shift shift: shifts) {
            List<Ride> rides = shift.getRides();
            for (Ride ride : rides) {
                System.err.println("test" + i);
                ReportDTO reportDTO = new ReportDTO();
                reportDTO.setTotalDays((int)TimeUnit.DAYS.convert((ride.getEndTime().getTime()-ride.getStartTime().getTime()), TimeUnit.MILLISECONDS) + 1);
                reportDTO.setRides("Ride " + i + " From " + ride.getStartTime() + " To " + ride.getEndTime());
                reportDTO.setTotalTimes((int)(ride.getEndTime().getTime() - ride.getStartTime().getTime()) / (1000 * 60 * 60));
                reportList.add(reportDTO);
            }
        }
        return reportList;
    }

}
