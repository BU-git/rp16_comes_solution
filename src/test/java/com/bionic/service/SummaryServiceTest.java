package com.bionic.service;

import com.bionic.config.RootConfig;
import com.bionic.config.TestPersistenceConfig;
import com.bionic.service.util.PeriodCalculator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import static org.junit.Assert.assertTrue;

/**
 * @author Pavel Boiko
 */
@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {RootConfig.class, TestPersistenceConfig.class},
        loader = AnnotationConfigWebContextLoader.class)
@Transactional
@Rollback
public class SummaryServiceTest {

    @Test
    public void testGetStartEndDate() {
        Date firstDate = PeriodCalculator.getPeriodStartTime(2016, 0);
        Date secondDate = PeriodCalculator.getPeriodEndTime(2016, 0);
        Date thirdDate = PeriodCalculator.getPeriodStartTime(2016, 1);
        System.out.println();
        PeriodCalculator.getWeekStartTime(2016, 1, 1);
        PeriodCalculator.getWeekEndTime(2016, 1, 1);
        PeriodCalculator.getWeekStartTime(2016, 1, 2);
        PeriodCalculator.getWeekEndTime(2016, 1, 2);
        PeriodCalculator.getWeekStartTime(2016, 1, 3);
        PeriodCalculator.getWeekEndTime(2016, 1, 3);
        PeriodCalculator.getWeekStartTime(2016, 1, 4);
        PeriodCalculator.getWeekEndTime(2016, 1, 4);
        PeriodCalculator.getWeekStartTime(2016, 2, 1);
        PeriodCalculator.getWeekEndTime(2016, 2, 1);
        assertTrue(secondDate.after(firstDate) && secondDate.before(thirdDate));
    }
}
