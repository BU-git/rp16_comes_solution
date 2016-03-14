package com.bionic;

import com.bionic.config.*;
import com.bionic.model.*;
import com.bionic.service.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    @SuppressWarnings("resource")
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig.class);
        UserService userService = context.getBean(UserService.class);
        User u1 = userService.findByUsername("pasha");
        System.out.println("password = " + u1.getPassword());
    }

}

