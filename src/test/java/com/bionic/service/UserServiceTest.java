package com.bionic.service;

import com.bionic.config.MainConfig;
import com.bionic.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

import static org.junit.Assert.*;

//@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MainConfig.class)
@Transactional
@Rollback(true) //??
public class UserServiceTest {

    @Inject
    private UserService userService;

    @Test
    public void testFindByUsername() throws Exception {
        String name = "test@test.com";
        User user = userService.findByUsername(name);
        assertEquals(user.getEmail(), name);
    }

    @Test
    public void testFindById() throws Exception {
        User user = userService.findById(3);
        assertEquals(user.getPassword(), "testpass");
    }

    @Test
    public void testGetAll() throws Exception {
        List<User> list = userService.getAll();
        assertNotNull(list);
        assertTrue(list.size() > 0);
    }

}
