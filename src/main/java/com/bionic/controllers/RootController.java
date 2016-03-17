package com.bionic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by user on 17.03.2016.
 */


/**
 * @author Sasha Chepurnoy
 */
@Controller
@RequestMapping("")
public class RootController {


    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String loginPageNavigation(ModelMap map){


        return "login";
    }




    //Class to get all input fields in one entity
    private class Login{
        String login,password;

        @Override
        public String toString() {
            return "Login{" +
                    "login='" + login + '\'' +
                    ", password='" + password + '\'' +
                    '}';
        }
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String loginProceed(@ModelAttribute("Login")
                               Login login, BindingResult result, ModelMap map){



        if(result.hasErrors()) return "login";

        System.out.println(login.toString());
        return "index";
    }

    @RequestMapping(value = "/register",method = RequestMethod.GET)
    public String registerPageNavigation(ModelMap map){


        return "register";
    }


}
