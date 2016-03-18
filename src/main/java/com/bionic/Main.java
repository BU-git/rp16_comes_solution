package com.bionic;

import com.bionic.config.MainConfig;
import com.bionic.exception.auth.impl.UserExistsException;
import com.bionic.model.Job;
import com.bionic.model.User;
import com.bionic.service.UserService;
import com.bionic.service.WorkScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Main {
    

    @Autowired
    private static BCryptPasswordEncoder passwordEncoder;

    @SuppressWarnings("resource")
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig.class);

        UserService userService = context.getBean(UserService.class);
        WorkScheduleService workScheduleService = context.getBean(WorkScheduleService.class);


        Job testJob = userService.findJobById(1);
        System.out.println(testJob.getJobName());

        String name= "test@test.com";
        User u2 = userService.findByUsername(name);


          try {
              userService.addUser(u2);
          }catch (UserExistsException e){
              System.out.println(e.getMessage());
          }

        //workschedule
//        WorkSchedule ws1 = workScheduleService.getById(1);
//        System.out.println("WorkSchedule1 = " + ws1.getCreationTime());
//        WorkSchedule ws2 = new WorkSchedule();
//        ws2.setCreationTime(ws1.getCreationTime());
//        ws2.setFriday("5");
//        ws2.setSunday("5");
//        ws2.setUser(u1);
//        workScheduleService.addWorkSchedule(ws2);
//        System.out.println("WorkSchedule2 id = " + ws2.getId());
//        System.out.println("WorkSchedule2 friday= " + ws2.getFriday());
//        ws2.setFriday("10");
//        workScheduleService.editWorchedule(ws2);
//        System.out.println("WorkSchedule2 friday new= " + ws2.getFriday());
//        workScheduleService.delete(ws2.getId());
//        System.out.println(workScheduleService.getById(ws2.getId()));


    }

}

