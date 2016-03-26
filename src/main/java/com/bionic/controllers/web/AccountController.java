package com.bionic.controllers.web;

import com.bionic.exception.auth.impl.UserNotExistsException;
import com.bionic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * author Dima Budko
 * v.0.1
 */
@Controller
public class AccountController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "verification", method = RequestMethod.GET)
    public String verifyUser(@RequestParam long key) {
        try {
            userService.enableAccount(key);
        } catch (UserNotExistsException ex) {
            return "verification_fail";
        }
        return "verification_success";
    }

    @RequestMapping(value = "password", method = RequestMethod.GET)
    public String passwordReset(@RequestParam long key) {
        try {
            userService.resetPassword(key);
            return "password_reset";
        } catch (UserNotExistsException ex) {
            return "verification_fail";
        }

    }

}
