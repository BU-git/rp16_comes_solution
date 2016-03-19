package com.bionic.controllers;

import com.bionic.exception.auth.impl.UserExistsException;
import com.bionic.model.User;
import com.bionic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * @author taras.yaroshchuk
 */
@RestController
@RequestMapping("/rest/api")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "users", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAllUsers() {
        return userService.getAll();
    }


    @RequestMapping(value = "user/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public User getUser(@PathVariable int id) {
        return userService.findById(id);
    }

    @RequestMapping(value = "user/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT) //204
    public void putUser(@PathVariable int id, @Valid User user) {
        try {
            userService.addUser(user);
        } catch (UserExistsException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "user/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT) //204
    public void deleteSpittle(@PathVariable int id) {
        userService.delete(id);
    }


    @RequestMapping(value = "user/",method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED) // HTTP 201 "Created"
    public User createUser(@Valid @RequestBody User user, BindingResult result, HttpServletResponse response) throws BindException {
        if (result.hasErrors()) {
            throw new BindException(result);
        }
        try {
            userService.addUser(user);
        } catch (UserExistsException e) {
            e.printStackTrace();
        }
        response.setHeader("Location", "/users/" + user.getId());
        return null;
    }
}
