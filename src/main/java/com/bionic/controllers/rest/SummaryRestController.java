package com.bionic.controllers.rest;

import com.bionic.dto.WorkingWeekDTO;
import com.bionic.exception.shift.impl.ShiftsFromFuturePeriodException;
import com.bionic.exception.shift.impl.ShiftsNotFoundException;
import com.bionic.model.User;
import com.bionic.service.ShiftService;
import com.bionic.service.SummaryService;
import com.bionic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Pavel Boiko
 */
@RestController
@RequestMapping("rest/api/users/{user_id}/summary/{year}/{number}")
public class SummaryRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private ShiftService shiftService;

    @Autowired
    private SummaryService summaryService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<WorkingWeekDTO> getSummaryForPeriod(@PathVariable("user_id") int userId,
                                                    @PathVariable("year") int year, @PathVariable("number") int number)
                                            throws ShiftsNotFoundException, ShiftsFromFuturePeriodException {

        User user = userService.findById(userId);
        if (user.isFourWeekPayOff()) {
            return summaryService.getSummaryForPeriod(userId, year, number);
        } else {
            return summaryService.getSummaryForMonth(userId, year, number);
        }
    }

}
