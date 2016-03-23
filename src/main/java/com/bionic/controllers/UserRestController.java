package com.bionic.controllers;

import com.bionic.exception.web.impl.UserNotFoundException;
import com.bionic.model.User;
import com.bionic.service.MailService;
import com.bionic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author taras.yaroshchuk
 */
@RestController
@RequestMapping("/rest/api/users")
public class UserRestController {

    @Autowired
    private UserService userService;

    @Autowired
    MailService mailService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAllUsers() {
        return userService.getAll();
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public User getUser(@PathVariable int id) {
        User user = userService.findById(id);
        if (user == null) throw new UserNotFoundException();
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


    @RequestMapping(value = "login", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public User login() throws UserNotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        User user = userService.findByUserEmail(name);
        if (user == null) throw new UserNotFoundException();
        return user;
    }
}
