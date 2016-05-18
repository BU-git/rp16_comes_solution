package com.bionic.controllers.web;

import com.bionic.exception.auth.impl.UserExistsException;
import com.bionic.model.User;
import com.bionic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.Date;

/**
 * @author Dima Budko
 */
@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public String start(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof AnonymousAuthenticationToken) {
            return "login";
        } else {
            return "index";
        }
    }

//    @RequestMapping(value = "/login",method = RequestMethod.GET)
//    public String loginPage(Model model){
//        return "login";
//    }


    @PreAuthorize("isAnonymous()")
    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("user",new User());
        return "registration";
    }

    @RequestMapping(value = "/addUser",method = RequestMethod.POST)
    public String addUser(@Valid @ModelAttribute("user")User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());
            return "registration";
        }
        user.setPasswordExpire(new Date(new Date().getTime() * 2));
        if (user.isZeroHours()) {
            user.setWorkSchedule(null);
        }
        if (user.getWorkSchedule() != null) {
            user.getWorkSchedule().setCreationTime(new Date());
        }
        System.out.println(user);
        try {
            System.out.println(user);
            userService.addUser(user);
        } catch (UserExistsException e) {
            return "login";
        }
        return "login";
    }

//    @RequestMapping("/*")
//    public String wrongUrlHandler() {
//        return "notFoundPage";
//    }

//    @RequestMapping("/home")
//    public String showHomepage() {
//        return "home";
//    }


}
