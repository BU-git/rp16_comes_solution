package com.bionic;

import com.bionic.config.RootConfig;
import com.bionic.model.User;
import com.bionic.service.MailService;
import com.bionic.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    @SuppressWarnings("resource")
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(RootConfig.class);

        UserService userService = context.getBean(UserService.class);
        MailService mailService = context.getBean(MailService.class);
        mailService.sendMail("dimonich41@gmail.com","Reset password","Link to input new password");
        User user = userService.findByUserEmail("dimonich41@gmail.com");

//        User user = userService.findById(7);
//        for(Job job : user.getJobs()) {
//            System.out.println(job.getJobName());
//            for (User us : job.getUsers()) {
//                us.getFirstName();
//            }
//        }
        /*WorkScheduleService workScheduleService = context.getBean(WorkScheduleService.class);


        Job testJob = userService.findJobById(1);
        System.out.println(testJob.getJobName());

        String name= "test@test.com";
        User u2 = userService.findByUsername(name);


          try {
              userService.addUser(u2);
          }catch (UserExistsException e){
              System.out.println(e.getMessage());
          }*/

        //workschedule
        /*WorkSchedule ws1 = workScheduleService.getById(1);
        System.out.println("WorkSchedule1 = " + ws1.getCreationTime());
        WorkSchedule ws2 = new WorkSchedule();
        ws2.setCreationTime(ws1.getCreationTime());
        ws2.setFriday("5");
        ws2.setSunday("5");
        ws2.setUser(u2);
        workScheduleService.addWorkSchedule(ws2);
        System.out.println("WorkSchedule2 id = " + ws2.getId());
        System.out.println("WorkSchedule2 friday= " + ws2.getFriday());
        ws2.setFriday("10");
        workScheduleService.editWorkSchedule(ws2);
        System.out.println("WorkSchedule2 friday new= " + ws2.getFriday());
        workScheduleService.delete(ws2.getId());
        System.out.println(workScheduleService.getById(ws2.getId()));
        workScheduleService.getAll()
                .forEach(d -> {
                            System.out.println(d.getCreationTime());
                            System.out.println(d.getId());
                        }
                );*/
    }

}

