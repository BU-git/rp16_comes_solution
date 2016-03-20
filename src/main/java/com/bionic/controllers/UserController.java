package com.bionic.controllers;

import com.bionic.exception.auth.impl.UserExistsException;
import com.bionic.exception.web.impl.UserNotFoundException;
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
@RequestMapping("/rest/api/users")
public class UserController {

    @Autowired
    private UserService userService;

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
    public void deleteSpittle(@PathVariable int id) {
        userService.delete(id);
    }


    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED) // HTTP 201 "Created"
    public User createUser(@Valid @RequestBody User user, BindingResult result, HttpServletResponse response) throws BindException {
        if (result.hasErrors()) {
            throw new BindException(result);
        }
        try {
            System.out.println(user);
            userService.addUser(user);
        } catch (UserExistsException e) {
            e.printStackTrace();
        }
        User savedUser = userService.findByUserEmail(user.getEmail());
        response.setHeader("Location", "/users/" + savedUser.getId());
        return savedUser;
    }
}
