package com.bionic.service;

import com.bionic.config.PersistenceConfig;
import com.bionic.config.RootConfig;
import com.bionic.dto.OvertimeDTO;
import com.bionic.dto.WorkingWeekDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Pavel Boiko
 */
@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {RootConfig.class, PersistenceConfig.class},
        loader = AnnotationConfigWebContextLoader.class)
@Transactional
@Rollback
public class OvertimeServiceTest {

    @Autowired
    private SummaryService summaryService;

    @Autowired
    private OvertimeService overtimeService;

    @Test
    public void getTestOverview() throws Exception {
        List<WorkingWeekDTO> summary = summaryService.getSummaryForMonth(61, 2016, 0);
        System.out.println(summary);
        System.out.println("=========");
//        for (int year = 1990; year < 2020; year++) {
//            for (int month = 0; month < 12; month++) {
//                Date monthStart = getMonthStartTime(year, month);
//                Date monthEnd = getMonthEndTime(year, month);
//                int numberOfWeeks = getWeeksBetween(monthStart, monthEnd);
//                for (int weekNumber = 1; weekNumber <= numberOfWeeks; weekNumber++) {
//                    System.out.println(getMonthWeekOfYear(year, month, weekNumber));
//                }
//            }
//        }
    }

    @Test
    public void getTestOvertime() throws Exception {
        List<OvertimeDTO> summary = overtimeService.getOvertimeForPeriod(3, 2016, 5);
        System.out.println(summary);
        System.out.println("=========");
    }
}
