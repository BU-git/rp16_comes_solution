package com.bionic.controllers.rest;

import com.bionic.dto.PasswordsDTO;
import com.bionic.exception.auth.impl.PasswordIncorrectException;
import com.bionic.exception.auth.impl.UserExistsException;
import com.bionic.exception.auth.impl.UserNotExistsException;
import com.bionic.model.User;
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

    @RequestMapping(value = "/reportLink/{year}/{period}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public void reportLink(@PathVariable("year") int year,@PathVariable("period") int period) throws UserNotExistsException {
        String email = userService.getAuthUser().getEmail();
        int userId = userService.getAuthUser().getId();
        mailService.sendReportLinks(email, userId, year, period);
    }

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
    @ResponseStatus(HttpStatus.OK) //200
    public User putUser(@PathVariable int id, @Valid @RequestBody User incomingUser) throws UserExistsException {
        User existingUser = userService.findByUserEmail(incomingUser.getEmail());
        if (existingUser != null && existingUser.getId() != id) throw new UserExistsException();

        userService.saveUser(incomingUser);
        User updatedUser = userService.findById(id);
        return updatedUser;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT) //204
    public void deleteUser(@PathVariable int id) {
        userService.delete(id);
    }


    @RequestMapping(value = "login", method = RequestMethod.GET)
    public ResponseEntity<User> login() {

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String name = auth.getName();
            User user = userService.findByUserEmail(name);

            if (user == null)
                return new ResponseEntity<User>(user, HttpStatus.EXPECTATION_FAILED);

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

    @RequestMapping(method = RequestMethod.GET,value = "{id}/verify")
    @ResponseStatus(HttpStatus.OK) // HTTP 200 "OK"
    public void verifyUser(@PathVariable int id)  {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String name = auth.getName();
        User user = userService.findById(id);
        userService.verifyUser(user);
    }

}
