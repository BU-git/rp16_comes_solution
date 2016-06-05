package com.bionic.service.impl;

import com.bionic.config.RootConfig;
import com.bionic.dto.AllowancesDTO;
import com.bionic.dao.ShiftDao;
import com.bionic.model.Ride;
import com.bionic.model.Shift;
import com.bionic.model.User;
import com.bionic.service.ReportService;
import com.bionic.service.UserService;
import com.bionic.service.util.MonthCalculator;
import com.bionic.service.util.PeriodCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
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
    private static final double FOR_BETWEEN_18_AND_24 = 2.66;
    private static final double FOR_12_HOURS_RIDE = 11.40;
    private static final double MULTIPLE_FIRST_DAY = 1.21;
    private static final double MULTIPLE_FIRST_DAY_BETWEEN_17_24 = 2.73;
    private static final double MULTIPLE_INTERIM_DAYS = 47.28;
    private static final double MULTIPLE_LAST_DAYS_BETWEEN_24_AND_6 = 1.21;
    private static final double MULTIPLE_LAST_DAYS_BETWEEN_24_AND_6_PAST_12 = 2.73;

    @Autowired
    private ShiftDao shiftDao;


    @Override
    public List<AllowancesDTO> getReportList(User user, int year, int period) {
        List<AllowancesDTO> reportList = new ArrayList<>();

        Date periodStartTime;
        Date periodEndTime;
        if (user.isFourWeekPayOff()) {
            periodStartTime = PeriodCalculator.getPeriodStartTime(year, period);
            periodEndTime = PeriodCalculator.getPeriodEndTime(year, period);
        } else {
            periodStartTime = MonthCalculator.getMonthStartTime(year,period);
            periodEndTime = MonthCalculator.getMonthEndTime(year,period);
        }
        System.err.println("testing " + shiftDao);
        List<Shift> shifts = shiftDao.getForPeriod(user.getId(), periodStartTime, periodEndTime);
        System.err.println("testing " + shifts);
        int i = 0;
        for (Shift shift : shifts) {
            List<Ride> rides = shift.getRides();
            if (rides != null) {
                i++;
                AllowancesDTO allowancesDTO = new AllowancesDTO();
                allowancesDTO.setRides("Ride " + i + " From " + rides.get(0).getStartTime() + " To " + rides.get(rides.size() - 1).getEndTime());
                allowancesDTO.setTotalDays((int) TimeUnit.DAYS.convert((rides.get(rides.size() - 1).getEndTime().getTime() - rides.get(0).getStartTime().getTime()), TimeUnit.MILLISECONDS) + 1);
                int totalTimes = 0;
                for (Ride ride : shift.getRides()) {
                    totalTimes += (ride.getEndTime().getTime() - ride.getStartTime().getTime()) / (1000 * 60 * 60);
                }
                allowancesDTO.setTotalTimes(totalTimes);
                allowancesDTO.setAllowances(getAllowances(allowancesDTO, shift));
                reportList.add(allowancesDTO);
            }
        }
        return reportList;
    }

    public double getAllowances(AllowancesDTO allowancesDTO, Shift shift) {
        double allowances = 0;
        List<Ride>  rides = shift.getRides();
        int totalHours = (int)(rides.get(rides.size() - 1).getEndTime().getTime() - rides.get(0).getStartTime().getTime()) / (1000 * 60 * 60);
        // Single day ride
        if (totalHours <= 24) {
            if (allowancesDTO.getTotalTimes() > 12) {
                allowances += FOR_12_HOURS_RIDE;
                System.err.println("test01 " + allowances);
            }
            if (allowancesDTO.getTotalTimes() > 4) {
                allowances += allowancesDTO.getTotalTimes() * FOR_LONGER_THAN_4_HOURS;
                System.err.println("test02 " + allowances);
            }
            for (Ride ride :rides) {
                if (ride.getStartTime().getHours() < 14) {
                    if (ride.getStartTime().getDate() != ride.getEndTime().getDate()) {
                        if (ride.getStartTime().getHours() >= 18) {
                            allowances += (FOR_BETWEEN_18_AND_24 - FOR_LONGER_THAN_4_HOURS) * (24 - ride.getStartTime().getHours());
                            System.err.println("test03 " + allowances);
                        } else {
                            allowances += (FOR_BETWEEN_18_AND_24 - FOR_LONGER_THAN_4_HOURS) * (24 - 18);
                        }
                        if (ride.getEndTime().getHours() >= 18) {
                            allowances += (FOR_BETWEEN_18_AND_24 - FOR_LONGER_THAN_4_HOURS) * (ride.getEndTime().getHours() - 18);
                            System.err.println("test04 " + allowances);
                        }
                    } else {
                        if (ride.getEndTime().getHours() >= 18) {
                            allowances += (FOR_BETWEEN_18_AND_24 - FOR_LONGER_THAN_4_HOURS) * (ride.getEndTime().getHours() - 18);
                            System.err.println("test05 " + allowances);
                        }
                    }
                }
            }
            // Multiple day ride
        } else {
            Date endDate = rides.get(0).getEndTime();
            allowances = 0;
            //Allowances per hour
            if (rides.get(0).getStartTime().getDate() == rides.get(1).getStartTime().getDate()) {
                allowances += (rides.get(0).getEndTime().getHours() - rides.get(0).getStartTime().getHours()) * MULTIPLE_FIRST_DAY;
                allowances += (rides.get(1).getEndTime().getHours() - rides.get(1).getStartTime().getHours()) * MULTIPLE_FIRST_DAY;
                if (rides.get(0).getStartTime().getHours() < 17) {
                    int hours = 24 - rides.get(0).getEndTime().getHours();
                    if (hours > 7) {
                        hours = 7;
                    }
                    allowances += hours * (MULTIPLE_FIRST_DAY_BETWEEN_17_24 - MULTIPLE_FIRST_DAY);
                    System.err.println("test1.1 " + allowances);
                }

                if (rides.get(1).getStartTime().getHours() < 17) {
                    int hours = 24 - rides.get(1).getEndTime().getHours();
                    if (hours > 7) {
                        hours = 7;
                    }
                    allowances += hours * (MULTIPLE_FIRST_DAY_BETWEEN_17_24 - MULTIPLE_FIRST_DAY);
                    System.err.println("test1 " + allowances);
                }

                endDate = rides.get(1).getEndTime();
            } else {
                allowances += (rides.get(0).getEndTime().getHours() - rides.get(0).getStartTime().getHours()) * MULTIPLE_FIRST_DAY;
                if (rides.get(0).getStartTime().getHours() < 17) {
                    int hours = rides.get(0).getEndTime().getHours() - 17;
                    System.err.println("testing1 " + hours);
                    System.err.println("testing2 " + allowances);
                    if (hours > 7) {
                        hours = 7;
                    }
                    allowances += hours * (MULTIPLE_FIRST_DAY_BETWEEN_17_24 - MULTIPLE_FIRST_DAY);
                    System.err.println("test1.2 " + allowances);
                }
                endDate = rides.get(0).getEndTime();
            }
            //Interim days
            allowances += (rides.get(rides.size()-1).getStartTime().getDate() - endDate.getDate() - 1) * MULTIPLE_INTERIM_DAYS;
            System.err.println("test3" + allowances);

            //Last day
            if (rides.get(rides.size()-1).getEndTime().getDate() == rides.get(rides.size()-2).getStartTime().getDate()) {
                if (rides.get(rides.size()-1).getStartTime().getHours() <= 6) {
                    int hours = rides.get(rides.size()-1).getEndTime().getHours() -  rides.get(rides.size()-1).getStartTime().getHours();
                    if (hours > 6) {
                        hours = 6;
                    }
                    if (rides.get(rides.size()-1).getEndTime().getHours() > 12) {
                        allowances += hours * MULTIPLE_LAST_DAYS_BETWEEN_24_AND_6_PAST_12;
                        System.err.println("test4" + allowances);
                    } else {
                        allowances += hours * MULTIPLE_LAST_DAYS_BETWEEN_24_AND_6;
                        System.err.println("test5" + allowances);
                    }
                }
                if (rides.get(rides.size()-2).getStartTime().getHours() <= 6) {
                    int hours = rides.get(rides.size()-2).getEndTime().getHours() -  rides.get(rides.size()-2).getStartTime().getHours();
                    if (hours > 6) {
                        hours = 6;
                    }
                    if (rides.get(rides.size()-1).getEndTime().getHours() > 12) {
                        allowances += hours * MULTIPLE_LAST_DAYS_BETWEEN_24_AND_6_PAST_12;
                        System.err.println("test6" + allowances);
                    } else {
                        allowances += hours * MULTIPLE_LAST_DAYS_BETWEEN_24_AND_6;
                        System.err.println("test7" + allowances);
                    }
                }
            } else {
                if (rides.get(rides.size()-1).getEndTime().getDate() == rides.get(rides.size()-1).getStartTime().getDate()) {
                    if (rides.get(rides.size()-1).getStartTime().getHours() <= 6) {
                        int hours = 0;
                        if (rides.get(rides.size()-1).getEndTime().getHours() <= 6) {
                            hours = rides.get(rides.size() - 1).getEndTime().getHours() - rides.get(rides.size() - 1).getStartTime().getHours();
                        } else {
                            hours = 6 - rides.get(rides.size() - 1).getStartTime().getHours();
                        }
                        if (hours > 6) {
                            hours = 6;
                        }
                        if (rides.get(rides.size()-1).getEndTime().getHours() > 12) {
                            allowances += hours * MULTIPLE_LAST_DAYS_BETWEEN_24_AND_6_PAST_12;
                            System.err.println("test8" + allowances);
                        } else {
                            allowances += hours * MULTIPLE_LAST_DAYS_BETWEEN_24_AND_6;
                            System.err.println("test9" + allowances);
                        }
                    }
                } else {
                    int hours = rides.get(rides.size()-1).getEndTime().getHours();
                    if (hours > 6) {
                        hours = 6;
                    }
                    if (rides.get(rides.size()-1).getEndTime().getHours() > 12) {
                        allowances += hours * MULTIPLE_LAST_DAYS_BETWEEN_24_AND_6_PAST_12;
                        System.err.println("test10" + allowances);
                    } else {
                        allowances += hours * MULTIPLE_LAST_DAYS_BETWEEN_24_AND_6;
                        System.err.println("test11" + allowances);
                    }
                }
            }
//            allowances += 24 * MULTIPLE_FIRST_DAY;
//            allowances += (allowancesDTO.getTotalTimes() / 24) * MULTIPLE_INTERIM_DAYS;
        }
        return allowances;
    }

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(RootConfig.class);
        UserService userService = context.getBean(UserService.class);
        User user = userService.findById(3);
//        ShiftDao shiftDao = context.getBean(ShiftDao.class);
//        Date periodStartTime = PeriodCalculator.getPeriodStartTime(2016, 5);
//        Date periodEndTime = PeriodCalculator.getPeriodEndTime(2016, 5);
//        System.err.println("Start time " + periodStartTime);
//        System.err.println("End time " + periodEndTime);
//        List<Shift> shifts = shiftDao.getForPeriod(user.getId(), periodStartTime, periodEndTime);
//        System.err.println("testing");
//        System.out.println(shifts);
//        System.err.println("testing");
        ReportService rs = context.getBean(ReportService.class);
        List<AllowancesDTO> list = rs.getReportList(user, 2016, 5);
        System.err.println("testing");
        System.out.println(list);
//        Date date = new Date();
//        date.setTime(2221231238133L);
//        System.out.println(date);
//        date.setHours(date.getHours() + 24);
//        System.out.println(date);

    }

}
