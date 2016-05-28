package com.bionic.controllers.rest;

import com.bionic.exception.auth.impl.UserNotExistsException;
import com.bionic.model.DayType;
import com.bionic.service.DayTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/rest/api/users/{user_id}/daytypes")
public class DayTypeRestController {

    @Autowired
    private DayTypeService dayTypeService;

    @RequestMapping(value = "/{day}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public DayType getDayType(@PathVariable("user_id") int user_id, @PathVariable("day") Date date) throws UserNotExistsException {
        return dayTypeService.getDayType(user_id, date);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public DayType addDayType(@Valid @RequestBody DayType dayType) throws UserNotExistsException {
        return dayTypeService.addDayType(dayType);
    }

    @RequestMapping(value = "/{daytype_id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.CREATED)
    public DayType updateDayType(@PathVariable("daytype_id") int daytypeId, @Valid @RequestBody DayType dayType) throws UserNotExistsException {
        return dayTypeService.addDayType(dayType);
    }
}
