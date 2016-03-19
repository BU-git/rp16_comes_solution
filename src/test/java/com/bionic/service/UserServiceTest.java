package com.bionic.service;

import com.bionic.config.RootConfig;
import com.bionic.config.WebConfig;
import com.bionic.model.User;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author taras.yaroshchuk
 */
//@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {RootConfig.class, WebConfig.class},
        loader = AnnotationConfigWebContextLoader.class)
@Transactional
@Rollback
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    public void testFindByUsername() throws Exception {
        String name = "test@test.com";
        User user = userService.findByUsername(name);
        assertEquals(user.getEmail(), name);
    }

    @Test
    public void testFindById() throws Exception {
        User user = userService.findById(4);
        assertEquals(user.getPassword(), "testpass");
    }

    @Test
    public void testGetAll() throws Exception {
        List<User> list = userService.getAll();
        assertNotNull(list);
        assertTrue(list.size() > 0);
    }

    @Ignore
    @Test
    public void testResetPassword() throws Exception {
        userService.resetPassword("boiko.pasha@gmail.com");
    }
}
