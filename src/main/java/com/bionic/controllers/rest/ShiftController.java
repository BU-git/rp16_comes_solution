package com.bionic.controllers.rest;

import com.bionic.exception.shift.impl.ShiftNoExistsException;
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
@RequestMapping("/rest/api/shift")
public class ShiftController {

    @Autowired
    private ShiftService shiftService;
    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Shift> getUserShifts(){
        int user =userService.getAuthUser().getId();
        return shiftService.getByUserId(user);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void createShift(@Valid @RequestBody Shift inputShift){
        User user= userService.getAuthUser();
        inputShift.setUser(user);
        shiftService.addShift(inputShift);
    }

    @RequestMapping(value = "{id}",method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteShift(@PathVariable final int id) throws ShiftNoExistsException {
        Integer user = userService.getAuthUser().getId();
        Shift ushift = ofNullable(shiftService.getById(id))
                .orElseThrow(() -> new ShiftNoExistsException(id));

        if (user.equals(ushift.getUser().getId())){
            shiftService.delete(id);
        } else{
           throw new AccessDeniedException("You Do not Have Permission to delete not YOUR Shift");
        }

    }

    @RequestMapping(value = "{id}",method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void editShift(@PathVariable final int id, @Valid @RequestBody Shift inputShift) throws ShiftNoExistsException{
        Integer user = userService.getAuthUser().getId();
        Shift ushift = ofNullable(shiftService.getById(id))
                .orElseThrow(() ->  new ShiftNoExistsException(id));
        if(user.equals(ushift.getUser().getId())){
            shiftService.editShift(inputShift);
        }else {
            throw new AccessDeniedException("You Do not Have Permission to Edit Not YOURS Shift");
        }

    }

}
