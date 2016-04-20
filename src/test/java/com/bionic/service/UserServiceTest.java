package com.bionic.service;

import com.bionic.config.RootConfig;
import com.bionic.config.TestPersistenceConfig;
import com.bionic.exception.auth.impl.PasswordIncorrectException;
import com.bionic.model.User;
import com.bionic.model.WorkSchedule;
import com.bionic.model.dict.Job;
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
@Rollback
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private WorkScheduleService workScheduleService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    static final Integer TEST_USERID = 1;
    static final String TEST_USERNAME = "test@test.com";
    static final String TEST_PASSWORD = "12345";

    @Test
    public void testAddUser() throws Exception {
        List<Job> jobs = new ArrayList<Job>();
        jobs.add(Job.DRIVER);
        jobs.add(Job.OPERATOR);

        User user = new User();
        user.setEmail("cscc@c.com");
        user.setPassword("12345");
        user.setFirstName("test");
        user.setLastName("test");
        user.setSex("Male");
        user.setFourWeekPayOff(true);
        user.setZeroHours(true);
        user.setContractHours(0);
        user.setEnabled(true);
        user.setVerified(true);
        user.setInsertion("lol");
        user.setPasswordExpire(new Date(System.currentTimeMillis() * 2));
        user.setJobs(jobs);

        User savedUser = userService.addUser(user);

        assertTrue(passwordEncoder.matches("12345", savedUser.getPassword()));
    }

    @Test
    public void testFindByUsername() throws Exception {
        User user = userService.findByUsername(TEST_USERNAME);

        assertEquals(user.getEmail(), TEST_USERNAME);
    }

    @Test
    public void testFindById() throws Exception {
        User user = userService.findById(1);
        System.out.println(user);

        assertTrue(user.getEmail().equals(TEST_USERNAME));
    }

    @Test
    public void testGetAll() throws Exception {
        List<User> list = userService.getAll();
        User firstUser = list.get(0);

        assertTrue(firstUser.getEmail().equals(TEST_USERNAME));
        assertFalse(list.isEmpty());
    }

    @Test
    public void testGetWorkScheduleOfUser() throws Exception {
        User user = userService.findById(1);
        WorkSchedule workSchedule = workScheduleService.getByUserId(1);

        assertNotNull(workSchedule);
        assertEquals(user.getWorkSchedule().getId(), workSchedule.getId());
    }

    @Test
    public void testGetWorkScheduleOfZeroHoursUser() throws Exception {
        User user = userService.findById(2);
        WorkSchedule workSchedule = workScheduleService.getByUserId(2);

        assertNull(workSchedule);
        assertNull(user.getWorkSchedule());
    }

    @Test(expected = PasswordIncorrectException.class)
    public void testChangeWrongPassword() throws Exception {
        User user = userService.findByUsername(TEST_USERNAME);
        userService.changePassword(user.getId(), "WRONG PASSWORD", "newPassword");
    }

    @Test
    public void testChangePassword() throws Exception {
        User user = userService.findByUsername(TEST_USERNAME);
        userService.changePassword(user.getId(), TEST_PASSWORD, "newPassword");

        assertTrue(passwordEncoder.matches("newPassword", user.getPassword()));
    }

    @Test
    public void testDelete() throws Exception {
        userService.delete(1);
    }

}
