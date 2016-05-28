package com.bionic.service.impl;

import com.bionic.controllers.report.ReportDTO;
import com.bionic.dao.ShiftDao;
import com.bionic.model.Ride;
import com.bionic.model.Shift;
import com.bionic.model.User;
import com.bionic.service.ReportService;
import com.bionic.service.util.PeriodCalculator;
import org.springframework.beans.factory.annotation.Autowired;
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

    private static final double FOR_LONGER_THAN_4_HOURS = 0.60;
    private static final double FOR_BEETWEEN_18_AND_24 = 2.66;
    private static final double FOR_12_HOURS_RIDE = 11.40;

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
                i++;
                ReportDTO reportDTO = new ReportDTO();
                reportDTO.setTotalDays((int)TimeUnit.DAYS.convert((ride.getEndTime().getTime()-ride.getStartTime().getTime()), TimeUnit.MILLISECONDS) + 1);
                reportDTO.setRides("Ride " + i + " From " + ride.getStartTime() + " To " + ride.getEndTime());
                reportDTO.setTotalTimes((int)(ride.getEndTime().getTime() - ride.getStartTime().getTime()) / (1000 * 60 * 60));
                reportDTO.setAllowances(getAllowances(reportDTO,ride));
                reportList.add(reportDTO);
            }
        }
        return reportList;
    }

    public double getAllowances(ReportDTO reportDTO,Ride ride) {
        double allowances = 0;

        // Single day ride
        if (reportDTO.getTotalTimes() <= 24) {
            if (reportDTO.getTotalTimes() > 4) {
                allowances += reportDTO.getTotalTimes() * FOR_LONGER_THAN_4_HOURS;
            }
            if (ride.getStartTime().getHours() < 14) {
                if (ride.getEndTime().getHours() > 18 && ride.getEndTime().getHours() < 24 && ride.getStartTime().getDate() == ride.getEndTime().getDate()) {
                    allowances += (ride.getEndTime().getHours() - 18) * FOR_BEETWEEN_18_AND_24;
                }
                if (reportDTO.getTotalTimes() > 12) {
                    allowances += FOR_12_HOURS_RIDE;
                }
            }
            // Multiple day ride
        } else {

        }
        System.out.println("time" + ride.getStartTime().getTime());
        return allowances;
    }

    public static void main(String[] args) {
//        ApplicationContext context = new AnnotationConfigApplicationContext(RootConfig.class);
//        ReportService reportService = context.getBean(ReportService.class);
        System.out.println(new Date(2015,5,4,23,1,2).getDay());

    }

}
