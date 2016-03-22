package com.bionic.controllers.web;

import com.bionic.exception.auth.impl.LinkUsedException;
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
public class PasswordResetController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "password", method = RequestMethod.GET)
    public String passwordReset(@RequestParam long key) throws LinkUsedException{
        userService.resetPassword(key);
        return "password_reset";
    }

}
