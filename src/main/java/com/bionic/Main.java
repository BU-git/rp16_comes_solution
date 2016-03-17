package com.bionic;

import com.bionic.config.MainConfig;
import com.bionic.model.Job;
import com.bionic.model.User;
import com.bionic.model.WorkSchedule;
import com.bionic.service.MailService;
import com.bionic.service.UserService;
import com.bionic.service.WorkScheduleService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Random;

public class Main {
    @SuppressWarnings("resource")
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig.class);
        UserService userService = context.getBean(UserService.class);
        WorkScheduleService workScheduleService = context.getBean(WorkScheduleService.class);
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


        //workschedule
        WorkSchedule ws1 = workScheduleService.getById(1);
        System.out.println("WorkSchedule1 = " + ws1.getCreationTime());
        WorkSchedule ws2 = new WorkSchedule();
        ws2.setCreationTime(ws1.getCreationTime());
        ws2.setFriday("5");
        ws2.setSunday("5");
        ws2.setUser(u1);
        workScheduleService.addWorkSchedule(ws2);
        System.out.println("WorkSchedule2 id = " + ws2.getId());
        System.out.println("WorkSchedule2 friday= " + ws2.getFriday());
        ws2.setFriday("10");
        workScheduleService.editWorchedule(ws2);
        System.out.println("WorkSchedule2 friday new= " + ws2.getFriday());
        workScheduleService.delete(ws2.getId());
        System.out.println(workScheduleService.getById(ws2.getId()));




=======
>>>>>>> 2bfa7983e44f1788f3bf5864a28599552cc0e236


//        MailService mail = context.getBean(MailService.class);
//        String sender = "comes.solutions@gmail.com";
//        String receiver = "comes.solutions@gmail.com";
//        Random random = new Random();
//        mail.sendMail(sender, receiver, "Test", "Hello! " + random.nextInt(1000) );
//        System.out.println("Done!");
    }

}

