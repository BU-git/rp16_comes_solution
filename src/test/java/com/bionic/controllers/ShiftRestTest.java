package com.bionic.controllers;

import com.bionic.config.RootConfig;
import com.bionic.config.WebConfig;
import com.bionic.model.Shift;
import com.bionic.service.ShiftService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author vitalii.levash
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {RootConfig.class, WebConfig.class},
        loader = AnnotationConfigWebContextLoader.class)
public class ShiftRestTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;
    @Autowired
    private ShiftService shiftService;

    private final static String TOKEN="Basic dGVzdEB0ZXN0LmNvbToxMjM0NQ==";

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).dispatchOptions(true)
                .addFilter(springSecurityFilterChain)
                .build();
    }

    @Test
    public void getUserShifts() throws Exception{
        mockMvc.perform(get("/rest/api/shift").header("Authorization",TOKEN))
                .andExpect(status().isOk());
    }
    @Test
    public void addShift() throws Exception{
        Shift shift= new Shift();

        shift.setStartTime(new Date(851032800000L));
        shift.setEndTime(new Date(851032800000L));

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(shift);

        System.out.println(json);

        mockMvc.perform(MockMvcRequestBuilders.post("/rest/api/shift")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization",TOKEN)
                .content(json)
         ).andDo(print()).andExpect(status().isCreated());

         //shiftService.deleteByUser(3);
    }
    @Ignore
    @Test
    public void editShift() throws Exception{

    }
    @Ignore
    @Test
    public void deleteShift() throws Exception{

    }
}