package com.bionic.controllers.rest;

import com.bionic.exception.shift.impl.ShiftNoExistsException;
import com.bionic.exception.shift.impl.ShiftOverlapsException;
import com.bionic.model.Shift;
import com.bionic.model.User;
import com.bionic.service.ShiftService;
import com.bionic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static java.util.Optional.ofNullable;

/**
 * @author vitalii.levash
 * @version 0.1
 */
@RestController
@RequestMapping("/rest/api/users/{user_id}/shifts")
public class ShiftRestController {

    @Autowired
    private ShiftService shiftService;
    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Shift> getUserShifts(@PathVariable("user_id") final int user_id) {
        return shiftService.getByUserId(user_id);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public int createShift(@Valid @RequestBody Shift inputShift, @PathVariable("user_id") final int user_id) throws ShiftOverlapsException {
        User user = userService.findById(user_id);
        inputShift.setUser(user);
        shiftService.addShift(inputShift);
        return inputShift.getId();
    }

    @RequestMapping(value = "{shift_id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteShift(@PathVariable final int shift_id, @PathVariable("user_id") final int user_id) throws ShiftNoExistsException {
        Integer user = userService.getAuthUser().getId();
        Shift ushift = ofNullable(shiftService.getById(shift_id))
                .orElseThrow(() -> new ShiftNoExistsException(shift_id));

        if (user.equals(ushift.getUser().getId())) {
            shiftService.delete(shift_id);
        } else {
            throw new AccessDeniedException("You Do not Have Permission to delete not YOUR Shift");
        }

    }

    @RequestMapping(value = "{shift_id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void editShift(@PathVariable final int shift_id, @Valid @RequestBody Shift inputShift, @PathVariable("user_id") final int user_id) throws ShiftNoExistsException {
        Integer userId = userService.getAuthUser().getId();
        Shift ushift = ofNullable(shiftService.getById(shift_id))
                .orElseThrow(() -> new ShiftNoExistsException(shift_id));
        if (userId.equals(user_id)) {
            inputShift.setUser(ushift.getUser());
            shiftService.editShift(inputShift);
        } else {
            throw new AccessDeniedException("You Do not Have Permission to Edit Not YOURS Shift");
        }

    }

}
