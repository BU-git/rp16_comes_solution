package com.bionic.service;

import com.bionic.config.RootConfig;
import com.bionic.config.TestPersistenceConfig;
import com.bionic.model.User;
import com.bionic.model.WorkSchedule;
import com.bionic.model.dict.Job;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author taras.yaroshchuk
 */
@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {RootConfig.class, TestPersistenceConfig.class},
        loader = AnnotationConfigWebContextLoader.class)
@Transactional
@Rollback(true)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private WorkScheduleService workScheduleService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    public void testAddUser() throws Exception {
        List<Job> jobs = new ArrayList<Job>();
        jobs.add(Job.DRIVER);
        jobs.add(Job.OPERATOR);

        User user = new User();
        user.setEmail("ccc@c.com");
        user.setPassword("12345");
        user.setFirstName("test");
        user.setLastName("test");
        user.setSex("Male");
        user.setFourWeekPayOff(true);
        user.setZeroHours(true);
        user.setContractHours(0);
        user.setEnabled(true);
        user.setVerified(true);
        user.setPasswordExpire(new Date(System.currentTimeMillis()*2));
        user.setJobs(jobs);

        User savedUser = userService.addUser(user);

        assertTrue(passwordEncoder.matches("12345", savedUser.getPassword()));
    }

    @Test
    public void testFindByUsername() throws Exception {
        String name = "test@test.com";
        User user = userService.findByUsername(name);
        System.out.println(user);
        assertEquals(user.getEmail(), name);
    }

    @Test
    public void testFindById() throws Exception {
        User user = userService.findById(25);
        System.out.println(user);
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writeValueAsString(user));
    }

    @Test
    public void testGetAll() throws Exception {
        List<User> list = userService.getAll();
        assertNotNull(list);
        assertTrue(list.size() > 0);
    }

    @Test
    public void testGetUsersWorkSchedule() throws Exception {
        User user = userService.findById(3);
        WorkSchedule workSchedule = workScheduleService.getByUserId(3);

        if (user.isZeroHours()) 
            assertNull(workSchedule);
        else assertNotNull(workSchedule);
    }

//    @Ignore
//    @Test
//    public void testResetPassword() throws Exception {
//        userService.resetPassword("boiko.pasha@gmail.com");
//    }

    @Test
    public void testChangePassword() throws Exception {
        User user = userService.findByUsername("boiko.pasha@gmail.com");
        user.setPassword(new BCryptPasswordEncoder().encode("1234"));
        userService.saveUser(user);
        userService.changePassword(user.getId(), "1234", "12345");
    }
}
