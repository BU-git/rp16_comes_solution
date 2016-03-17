package com.bionic;

import com.bionic.config.MainConfig;
import com.bionic.model.Job;
import com.bionic.model.User;
import com.bionic.service.MailService;
import com.bionic.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Random;

public class Main {
    @SuppressWarnings("resource")
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig.class);
        UserService userService = context.getBean(UserService.class);
        User u1 = userService.findById(3);
        System.out.println("password = " + u1.getPassword());
        System.out.println("Employer's name = " + u1.getEmployer().getName());

        List<User> list = userService.getAll();
        for (User user : list) {
            System.out.println("Test list" + user.getFirstName());
        }



        String name=new String("test@test.com");
        User u2 = userService.findByUsername(name);
        u2.setId(5);
        if (userService.findByUsername(name).getId()==0) {
            userService.addUser(u2);
        } else {
            System.out.println("User exists");
        }




//        MailService mail = context.getBean(MailService.class);
//        String sender = "comes.solutions@gmail.com";
//        String receiver = "comes.solutions@gmail.com";
//        Random random = new Random();
//        mail.sendMail(sender, receiver, "Test", "Hello! " + random.nextInt(1000) );
//        System.out.println("Done!");
    }

}

