package com.bionic.service;

import com.bionic.config.PersistenceConfig;
import com.bionic.config.RootConfig;
import com.bionic.dto.WorkingWeekDTO;
import com.bionic.service.util.WeekCalculator;
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

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.bionic.service.util.MonthCalculator.getMonthEndTime;
import static com.bionic.service.util.MonthCalculator.getMonthStartTime;
import static com.bionic.service.util.PeriodCalculator.getPeriodEndTime;
import static com.bionic.service.util.PeriodCalculator.getPeriodStartTime;
import static com.bionic.service.util.WeekCalculator.*;
import static org.junit.Assert.assertTrue;

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
public class SummaryServiceTest {

    @Autowired
    private SummaryService summaryService;

    @Test
    public void testPeriodGetStartEndDate() {
        Date firstDate = getPeriodStartTime(2016, 0);
        Date secondDate = getPeriodEndTime(2016, 0);
        Date thirdDate = getPeriodStartTime(2016, 1);
        System.out.println();
        getPeriodWeekStartTime(2016, 1, 1);
        getPeriodWeekEndTime(2016, 1, 1);
        getPeriodWeekStartTime(2016, 1, 2);
        getPeriodWeekEndTime(2016, 1, 2);
        getPeriodWeekStartTime(2016, 1, 3);
        getPeriodWeekEndTime(2016, 1, 3);
        getPeriodWeekStartTime(2016, 1, 4);
        getPeriodWeekEndTime(2016, 1, 4);
        getPeriodWeekStartTime(2016, 2, 1);
        getPeriodWeekEndTime(2016, 2, 1);
        assertTrue(secondDate.after(firstDate) && secondDate.before(thirdDate));
    }

    @Test
    public void testMonthStartEndDate() {
        int month1 = Calendar.MARCH;
        int month2 = Calendar.APRIL;
        Date firstDate = getMonthStartTime(2016, month1);
        Date secondDate = getMonthEndTime(2016, month1);
        Date thirdDate = getMonthStartTime(2016, month2);
        int numberOfWeeks = getWeeksBetween(firstDate, secondDate);
        for (int i = 1; i <= numberOfWeeks; i++) {
            getMonthWeekStartTime(2016, month1, i);
            getMonthWeekEndTime(2016, month1, i);
        }

        System.out.println(WeekCalculator.getWeeksBetween(firstDate, secondDate));
        assertTrue(secondDate.after(firstDate) && secondDate.before(thirdDate));
    }

    @Test
    public void getTestSummary() throws Exception {
        List<WorkingWeekDTO> summary = summaryService.getSummaryForPeriod(61, 2016, 1);
        System.out.println(summary);
    }
}
