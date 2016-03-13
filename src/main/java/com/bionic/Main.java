package com.bionic;

import com.bionic.model.User;
import com.bionic.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    @SuppressWarnings("resource")
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        UserService userService = context.getBean(UserService.class);
        User u1 = userService.findByUsername("pasha");
        System.out.println("password = " + u1.getPassword());
    }

}

