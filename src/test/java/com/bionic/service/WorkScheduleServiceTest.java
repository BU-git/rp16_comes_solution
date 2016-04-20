package com.bionic.service;

import com.bionic.config.RootConfig;
import com.bionic.config.TestPersistenceConfig;
import com.bionic.model.WorkSchedule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static com.bionic.service.UserServiceTest.TEST_USERID;
import static org.junit.Assert.assertTrue;


/**
 * @author taras.yaroshchuk
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {RootConfig.class, TestPersistenceConfig.class},
        loader = AnnotationConfigWebContextLoader.class)
@Transactional
@Rollback
public class WorkScheduleServiceTest {

    @Autowired
    private WorkScheduleService workScheduleService;

    @Test
    public void testFindById() throws Exception {
        WorkSchedule workSchedule = workScheduleService.getById(1);
        assertTrue(workSchedule.getId().equals(1));
    }

    @Test
    public void testDelete() throws Exception {
        workScheduleService.delete(1);
    }

    @Test
    public void testGetByUserId() throws Exception {
        WorkSchedule workSchedule = workScheduleService.getByUserId(TEST_USERID);
        assertTrue(workSchedule.getId().equals(1));
    }
}
