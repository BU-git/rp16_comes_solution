package com.bionic.controllers;

import com.bionic.config.RootConfig;
import com.bionic.config.WebConfig;
import com.bionic.service.UserService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author vitalii.levash
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {RootConfig.class, WebConfig.class},
        loader = AnnotationConfigWebContextLoader.class)
public class AuthRestTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserService userService;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).dispatchOptions(true).build();
    }

    @Ignore
    @Test
    public void createUser() throws Exception {
        String request = new String("{\"id\":700,\"password\":\"123\"\",\"email\":\"new@Testernew.com\",\"firstName\":\"first\",\"lastName\":\"last\",\"insertion\":\"ins\",\"sex\":\"Male\",\"fourWeekPayOff\":true,\"zeroHours\":true,\"contractHours\":20,\"enabled\":true,\"birthDate\":851032800000,\"passwordExpire\":1447978200000,\"workSchedule\":{\"id\":1,\"creationTime\":1420149780000,\"sunday\":\"5\",\"monday\":\"6\",\"tuesday\":\"7\",\"wednesday\":\"8\",\"thursday\":\"9\",\"friday\":\"10\",\"saturday\":\"11\"},\"role\":\"ADMIN\",\"employer\":{\"id\":1,\"name\":\"test\"},\"jobs\":[{\"id\":1,\"jobName\":\"testJob\"}]}");

         mockMvc.perform(post("/rest/api/auth/").content(request)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())

                //.andExpect(jsonPath("$.identifier", equalTo("123")))
                //.andExpect(jsonPath("$.allPeople[*].firstName", hasItem("Tom"))
         ;

    }


}
