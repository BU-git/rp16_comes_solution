package com.bionic.service;

import com.bionic.config.MainConfig;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.util.Random;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MainConfig.class)
public class MailServiceTest {

    @Inject
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
