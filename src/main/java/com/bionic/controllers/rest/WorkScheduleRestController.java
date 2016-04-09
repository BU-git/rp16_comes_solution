package com.bionic.controllers.rest;

import com.bionic.exception.auth.impl.UserNotExistsException;
import com.bionic.model.User;
import com.bionic.model.WorkSchedule;
import com.bionic.service.UserService;
import com.bionic.service.WorkScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/rest/api/users/{user_id}/workschedule")
public class WorkScheduleRestController {

    @Autowired
    private WorkScheduleService workScheduleService;

    @Autowired
    private UserService userService;


    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public WorkSchedule getUsersWorkSchedule(@PathVariable("user_id") int user_id) throws UserNotExistsException {
        return workScheduleService.getByUserId(user_id);
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK) //200
    public void putUsersWorkSchedule(@PathVariable int id, @Valid @RequestBody WorkSchedule workSchedule, @PathVariable("user_id") int user_id) {
        User user = userService.findById(id);
        user.setWorkSchedule(workSchedule);
        workScheduleService.saveWorkSchedule(workSchedule);
        userService.saveUser(user);
    }
}
