package com.bionic.service;

import com.bionic.config.MailConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

/**
 * @author taras.yaroshchuk
 */
@Service
public class MailService {
    @Autowired
    private MailSender mailSender;

    public void sendMail(String to, String subject, String msg){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(MailConfig.MAIL_USERNAME);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(msg);
        mailSender.send(message);
    }
}
