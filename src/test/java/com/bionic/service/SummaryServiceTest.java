package com.bionic.service;

import com.bionic.config.PersistenceConfig;
import com.bionic.config.RootConfig;
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

import java.util.Date;
import java.util.List;

import static com.bionic.service.util.PeriodCalculator.getPeriodEndTime;
import static com.bionic.service.util.PeriodCalculator.getPeriodStartTime;
import static com.bionic.service.util.WeekCalculator.getWeekEndTime;
import static com.bionic.service.util.WeekCalculator.getWeekStartTime;
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
    public void testGetStartEndDate() {
        Date firstDate = getPeriodStartTime(2016, 4);
        Date secondDate = getPeriodEndTime(2016, 4);
        Date thirdDate = getPeriodStartTime(2016, 5);
        System.out.println();
        getWeekStartTime(2016, 1, 1);
        getWeekEndTime(2016, 1, 1);
        getWeekStartTime(2016, 1, 2);
        getWeekEndTime(2016, 1, 2);
        getWeekStartTime(2016, 1, 3);
        getWeekEndTime(2016, 1, 3);
        getWeekStartTime(2016, 1, 4);
        getWeekEndTime(2016, 1, 4);
        getWeekStartTime(2016, 2, 1);
        getWeekEndTime(2016, 2, 1);
        assertTrue(secondDate.after(firstDate) && secondDate.before(thirdDate));
    }

    @Test
    public void getTestSummary() throws Exception {
        List<WorkingWeekDTO> summary = summaryService.getSummary(35, 2016, 5);
        System.out.println(summary);
    }
}
