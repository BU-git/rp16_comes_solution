package com.bionic.controllers.rest;

import com.bionic.exception.auth.impl.UserExistsException;
import com.bionic.exception.auth.impl.UserNotExistsException;
import com.bionic.model.User;
import com.bionic.service.MailService;
import com.bionic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @author taras.yaroshchuk
 */

@RestController
@RequestMapping("/rest/api/auth")
public class AuthenticationRestController {
    @Autowired
    private UserService userService;

    @Autowired
    MailService mailService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED) // HTTP 201 "Created"
    public User createUser(@Valid @RequestBody User user,  HttpServletResponse response) throws BindException, UserExistsException {
        /*
        if (result.hasErrors()) {
            throw new BindException(result);
        }
        */
        System.out.println(user);
        userService.addUser(user);
        User savedUser = userService.findByUserEmail(user.getEmail());
        response.setHeader("Location", "/users/" + savedUser.getId());
        return savedUser;
    }



    @RequestMapping(value = "password", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void resetLink(@RequestBody String email) throws UserNotExistsException {
        userService.resetLink(email);
    }

    @RequestMapping(value = "exist", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public void isUserExist(@RequestParam String email) throws UserExistsException {
        User user = userService.findByUserEmail(email);
        if (user != null) throw new UserExistsException();
    }
}
