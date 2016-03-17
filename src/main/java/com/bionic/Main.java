package com.bionic;

import com.bionic.config.MainConfig;
import com.bionic.model.Job;
import com.bionic.model.User;
import com.bionic.service.MailService;
import com.bionic.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Random;

public class Main {
    @SuppressWarnings("resource")
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig.class);
        UserService userService = context.getBean(UserService.class);
        User u1 = userService.findById(3);
        System.out.println("password = " + u1.getPassword());
        System.out.println("Employer's name = " + u1.getEmployer().getName());
        User u2 = userService.findByUsername("test@test.com");
        u2.setId(4);
        userService.addUser(u2);




//        MailService mail = context.getBean(MailService.class);
//        String sender = "comes.solutions@gmail.com";
//        String receiver = "comes.solutions@gmail.com";
//        Random random = new Random();
//        mail.sendMail(sender, receiver, "Test", "Hello! " + random.nextInt(1000) );
//        System.out.println("Done!");
    }

}

