package com.bionic.controllers.rest;

import com.bionic.dto.PasswordsDTO;
import com.bionic.exception.auth.impl.*;
import com.bionic.model.User;
import com.bionic.model.WorkSchedule;
import com.bionic.service.MailService;
import com.bionic.service.UserService;
import com.bionic.service.WorkScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

import static com.bionic.service.UserService.ONE_HOUR;

/**
 * @author taras.yaroshchuk
 */
@RestController
@RequestMapping("/rest/api/users")
public class UserRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private WorkScheduleService workScheduleService;

    @Autowired
    MailService mailService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAllUsers() {
        return userService.getAll();
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public User getUser(@PathVariable int id) throws UserNotExistsException {
        User user = userService.findById(id);
        if (user == null) throw new UserNotExistsException();
        return user;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT) //204
    public void putUser(@PathVariable int id, @Valid @RequestBody User user) {
        userService.saveUser(user);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT) //204
    public void deleteUser(@PathVariable int id) {
        userService.delete(id);
    }

    @RequestMapping(value = "{id}/workschedule", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public WorkSchedule getUsersWorkSchedule(@PathVariable int id) throws UserNotExistsException {
        return workScheduleService.getByUserId(id);
    }

    @RequestMapping(value = "{id}/workschedule", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT) //204
    public void putUsersWorkSchedule(@PathVariable int id, @Valid @RequestBody WorkSchedule workSchedule) {
        User user = userService.findById(id);
        user.setWorkSchedule(workSchedule);
        workScheduleService.saveWorkSchedule(workSchedule);
        userService.saveUser(user);
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public ResponseEntity<User> login() {

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String name = auth.getName();
            User user = userService.findByUserEmail(name);

            if (user == null)
                return new ResponseEntity<User>(user, HttpStatus.NOT_FOUND);

            if (user.getPasswordExpire().before(new Date()))
                return new ResponseEntity<User>(user, HttpStatus.FORBIDDEN);

            if (user.getPasswordExpire().before(new Date(System.currentTimeMillis() + ONE_HOUR)))
                return new ResponseEntity<User>(user, HttpStatus.CONFLICT);

            if (!user.isVerified())
                return new ResponseEntity<User>(user, HttpStatus.PRECONDITION_FAILED);

            return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "{id}/password", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(@PathVariable int id, @RequestBody PasswordsDTO passwordsDTO) throws UserNotExistsException, PasswordIncorrectException {


        userService.changePassword(id, passwordsDTO.getOldPassword(), passwordsDTO.getNewPassword());
    }

    @RequestMapping(method = RequestMethod.GET,value = "verify")
    @ResponseStatus(HttpStatus.OK) // HTTP 200 "OK"
    public void verifyUser()  {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        User user = userService.findByUserEmail(name);
        userService.verifyUser(user);
    }

}
