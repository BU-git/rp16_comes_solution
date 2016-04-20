package com.bionic.service;

import com.bionic.config.RootConfig;
import com.bionic.config.TestPersistenceConfig;
import com.bionic.model.Ride;
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

import static org.junit.Assert.assertNotNull;
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
public class RideServiceTest {

    @Autowired
    private RideService rideService;

    @Test
    public void testFindById() throws Exception {
        Ride ride = rideService.getById(1);
        assertNotNull(ride);
        assertTrue(ride.getId().equals(1));
    }

    @Test
    public void testDelete() throws Exception {
        rideService.delete(1);
    }

    @Test
    public void testGetByShiftId() throws Exception {
        List<Ride> rides = rideService.getByShiftId(1);
        assertTrue(rides.size() > 1);
    }
}