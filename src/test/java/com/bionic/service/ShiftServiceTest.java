package com.bionic.service;

import com.bionic.config.RootConfig;
import com.bionic.config.TestPersistenceConfig;
import com.bionic.model.Shift;
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

import static com.bionic.service.UserServiceTest.TEST_USERID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author taras.yaroshchuk
 */
@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {RootConfig.class, TestPersistenceConfig.class},
        loader = AnnotationConfigWebContextLoader.class)
@Transactional
@Rollback
public class ShiftServiceTest {

    @Autowired
    private ShiftService shiftService;

    @Test
    public void testGetByUserId() throws Exception {
        List<Shift> shifts = shiftService.getByUserId(TEST_USERID);
        Shift firstShift = shifts.get(0);
        assertTrue(firstShift.getId().equals(1));
        assertEquals(TEST_USERID, firstShift.getUser().getId());
    }
}