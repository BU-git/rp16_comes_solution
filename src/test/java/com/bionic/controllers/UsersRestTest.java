package com.bionic.controllers;

import com.bionic.config.RootConfig;
import com.bionic.config.WebConfig;
import com.bionic.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author vitalii.levash.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {RootConfig.class, WebConfig.class},
        loader = AnnotationConfigWebContextLoader.class)
public class UsersRestTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserService userService;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    private final static String TOKEN="Basic dGVzdEB0ZXN0LmNvbToxMjM0NQ==";

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).dispatchOptions(true)
                .addFilter(springSecurityFilterChain)
                .build();
    }
    @Test
    public void accessDenid() throws Exception{
        mockMvc.perform(get("/rest/api/users"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void findAllUsersRest() throws Exception{
        mockMvc.perform(get("/rest/api/users").header("Authorization",TOKEN))
                .andExpect(status().isOk());
    }

    @Test
    public void findUserById() throws Exception{
        mockMvc.perform(get("/rest/api/users/7").header("Authorization",TOKEN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id",is(7)));
    }

    @Test
    public void findUserByIdNotFound() throws Exception{
        mockMvc.perform(get("/rest/api/users/1000").header("Authorization",TOKEN))
                .andExpect(status().isNotFound());
    }

//    @Test
//    public void login() throws Exception{
//
//    }

    @Test
    public void notVerified() throws Exception {
        com.bionic.model.User user = userService.findByUserEmail("test@test.com");
        if (!user.isVerified()) {
            mockMvc.perform(get("/rest/api/users/login").header("Authorization", TOKEN))
                    .andExpect(status().isForbidden());
        } else {
            mockMvc.perform(get("/rest/api/users/login").header("Authorization",TOKEN))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.email",is("test@test.com")));
        }
    }
}
