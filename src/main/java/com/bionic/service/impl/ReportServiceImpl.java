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
    private static final double MULTIPLE_FIRST_DAY = 1.21;
    private static final double MULTIPLE_INTERIM_DAYS = 47.28;

    @Autowired
    private ShiftDao shiftDao;


    @Override
    public List<ReportDTO> getReportList(User user, int year, int period) {
        List<ReportDTO> reportList = new ArrayList<>();
        Date periodStartTime = PeriodCalculator.getPeriodStartTime(year, period);
        Date periodEndTime = PeriodCalculator.getPeriodEndTime(year, period);
        List<Shift> shifts = shiftDao.getForPeriod(user.getId(), periodStartTime, periodEndTime);
        int i = 0;
        for (Shift shift : shifts) {
            i++;
            List<Ride> rides = shift.getRides();
            ReportDTO reportDTO = new ReportDTO();
            reportDTO.setRides("Ride " + i + " From " + rides.get(0).getStartTime() + " To " + rides.get(rides.size() - 1).getEndTime());
            reportDTO.setTotalDays((int) TimeUnit.DAYS.convert((rides.get(rides.size() - 1).getEndTime().getTime() - rides.get(0).getStartTime().getTime()), TimeUnit.MILLISECONDS) + 1);
            int totalTimes = 0;
            for (Ride ride : shift.getRides()) {
                totalTimes += (ride.getEndTime().getTime() - ride.getStartTime().getTime()) / (1000 * 60 * 60);
            }
            reportDTO.setTotalTimes(totalTimes);
            reportDTO.setAllowances(getAllowances(reportDTO, shift));
            reportList.add(reportDTO);
        }
        return reportList;
    }

    public double getAllowances(ReportDTO reportDTO, Shift shift) {
        double allowances = 0;
        List<Ride> rides = shift.getRides();
        int totalHours = (int)(rides.get(rides.size() - 1).getEndTime().getTime() - rides.get(0).getStartTime().getTime()) / (1000 * 60 * 60);
        // Single day ride
        if (totalHours <= 24) {
            if (reportDTO.getTotalTimes() > 12) {
                allowances += FOR_12_HOURS_RIDE;
            }
            if (reportDTO.getTotalTimes() > 4) {
                allowances += reportDTO.getTotalTimes() * FOR_LONGER_THAN_4_HOURS;
            }
            for (Ride ride :rides) {
                if (ride.getStartTime().getHours() < 14) {
                    if (ride.getStartTime().getDate() != ride.getEndTime().getDate()) {
                        if (ride.getStartTime().getHours() >= 18) {
                            allowances += FOR_BEETWEEN_18_AND_24 * (24 - ride.getStartTime().getHours());
                        }
                        if (ride.getEndTime().getHours() >= 18) {
                            allowances += FOR_BEETWEEN_18_AND_24 * (ride.getEndTime().getHours() - 18);
                        }
                    } else {
                        if (ride.getEndTime().getHours() >= 18) {
                            allowances += FOR_BEETWEEN_18_AND_24 * (ride.getEndTime().getHours() - 18);
                        }
                    }
                }
            }
            // Multiple day ride
            //TODO
        } else {
            allowances = 0;
//            allowances += 24 * MULTIPLE_FIRST_DAY;
//            allowances += (reportDTO.getTotalTimes() / 24) * MULTIPLE_INTERIM_DAYS;
        }
        return allowances;
    }

    public static void main(String[] args) {
//        ApplicationContext context = new AnnotationConfigApplicationContext(RootConfig.class);
//        ReportService reportService = context.getBean(ReportService.class);


    }

}
