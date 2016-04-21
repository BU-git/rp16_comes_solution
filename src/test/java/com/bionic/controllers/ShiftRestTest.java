package com.bionic.controllers;

import com.bionic.config.RootConfig;
import com.bionic.config.TestPersistenceConfig;
import com.bionic.config.WebConfig;
import com.bionic.model.Ride;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author vitalii.levash
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {RootConfig.class, TestPersistenceConfig.class, WebConfig.class},
        loader = AnnotationConfigWebContextLoader.class)
public class ShiftRestTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    private ShiftService shiftService;

    private final static String TOKEN = "Basic dGVzdEB0ZXN0LmNvbToxMjM0NQ==";

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).dispatchOptions(true)
                .addFilter(springSecurityFilterChain)
                .build();
    }

    @Test
    public void addEditAndDeleteShift() throws Exception {
        //CREATE SHIFT
        Shift shift = new Shift();

        shift.setStartTime(new Date(851032800000L));
        shift.setEndTime(new Date(851032800000L));
        shift.setPause(851032800000L);

        Ride ride = new Ride();
        ride.setStartTime(new Date(851032800000L));
        ride.setEndTime(new Date(851032800000L));

        Ride ride_end = new Ride();
        ride_end.setStartTime(new Date(821032800000L));
        ride_end.setEndTime(new Date(851032800000L));

        List<Ride> list = new ArrayList<>();
        list.add(ride);
        list.add(ride_end);

        shift.setRides(list);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(shift);

        System.out.println(json);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/rest/api/users/{user_id}/shifts", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", TOKEN)
                .content(json)
        ).andDo(print()).andExpect(status().isCreated()).andReturn();

        int shiftId = Integer.parseInt(result.getResponse().getContentAsString());

        //EDIT SHIFT
        json = "{\"id\":" + shiftId + ",\"startTime\":851032800000,\"endTime\":851032800000,\"pause\":851032800000}";

        mockMvc.perform(put("/rest/api/users/{user_id}/shifts/{shify_id}", 1, shiftId).header("Authorization", TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
                .andDo(print())
                .andExpect(status().isOk());

        //DELETE SHIFT
        mockMvc.perform(delete("/rest/api/users/{user_id}/shifts/{shify_id}", 1, shiftId).header("Authorization", TOKEN))
                .andDo(print())
                .andExpect(status().isOk());


    }

    @Test
    public void getUserShifts() throws Exception {
        mockMvc.perform(get("/rest/api/users/{user_id}/shifts", 1).header("Authorization", TOKEN))
                .andExpect(status().isOk());
    }


    @Test
    public void editShiftDenid() throws Exception {
        int shiftId = 1;
        String json = "{\"id\":" + shiftId + ",\"startTime\":851032800000,\"endTime\":881032800000,\"pause\":881032899}";

        System.out.println(json);
        mockMvc.perform(put("/rest/api/users/{user_id}/shifts/{shift_id}", 2, shiftId)
                .header("Authorization", TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}
