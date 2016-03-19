package com.bionic.service;

import com.bionic.config.RootConfig;
import com.bionic.config.WebConfig;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Random;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {RootConfig.class, WebConfig.class},
        loader = AnnotationConfigWebContextLoader.class)
public class MailServiceTest {

    @Autowired
    private MailService mailService;

    @Ignore
    @Test
    public void testSendMail() {
        String receiver = "comes.solutions@gmail.com";
        Random random = new Random();
        mailService.sendMail(receiver, "Test", "Hello! " + random.nextInt(1000) );
        System.out.println("Done!");
    }

}
