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

    @RequestMapping(value = "{user_id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public User getUser(@PathVariable int user_id) throws UserNotExistsException {
        User user = userService.findById(user_id);
        if (user == null) throw new UserNotExistsException();
        return user;
    }

    @RequestMapping(value = "{user_id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK) //200
    public User putUser(@PathVariable int user_id, @Valid @RequestBody User incomingUser) throws UserExistsException {
        User existingUser = userService.findByUserEmail(incomingUser.getEmail());
        if (existingUser != null && existingUser.getId() != user_id) throw new UserExistsException();

        if (incomingUser.getWorkSchedule() != null) {
            WorkSchedule workSchedule = workScheduleService.saveWorkSchedule(incomingUser.getWorkSchedule());
            incomingUser.setWorkSchedule(workSchedule);
        }
        if (incomingUser.isZeroHours() && !existingUser.isZeroHours())
            workScheduleService.delete(existingUser.getWorkSchedule());

        userService.saveUser(incomingUser);
        User updatedUser = userService.findById(user_id);
        return updatedUser;
    }

    @RequestMapping(value = "{user_id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT) //204
    public void deleteUser(@PathVariable int user_id) {
        userService.delete(user_id);
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public ResponseEntity<User> login() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        User user = userService.findByUserEmail(name);

        if (user == null)
            return new ResponseEntity<>(user, HttpStatus.NOT_FOUND);

        if (user.getPasswordExpire().before(new Date()))
            return new ResponseEntity<>(user, HttpStatus.FORBIDDEN);

        if (user.getPasswordExpire().before(new Date(System.currentTimeMillis() + ONE_HOUR)))
            return new ResponseEntity<>(user, HttpStatus.CONFLICT);

        if (!user.isVerified())
            return new ResponseEntity<>(user, HttpStatus.PRECONDITION_FAILED);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "{user_id}/password", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(@PathVariable int user_id, @RequestBody PasswordsDTO passwordsDTO) throws UserNotExistsException, PasswordIncorrectException {
        userService.changePassword(user_id, passwordsDTO.getOldPassword(), passwordsDTO.getNewPassword());
    }

    @RequestMapping(method = RequestMethod.GET, value = "{user_id}/verify")
    @ResponseStatus(HttpStatus.OK) // HTTP 200 "OK"
    public void verifyUser(@PathVariable int user_id) {
        User user = userService.findById(user_id);
        userService.verifyUser(user);
    }

}
