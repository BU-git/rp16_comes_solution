package com.bionic.controllers;

import com.bionic.model.User;
import com.bionic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author vitalii.levash
 */
@RestController
@RequestMapping(value = "/user")
public class TestRestController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/{userid}", method = RequestMethod.GET)
    @ResponseBody
    public User getUserInfo(@PathVariable("userid") String userid){
      return userService.findById(Integer.valueOf(userid));
    }
}
