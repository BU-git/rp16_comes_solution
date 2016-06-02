package com.bionic.service.impl;

import com.bionic.config.MailConfig;
import com.bionic.dao.UserKeyDao;
import com.bionic.service.MailService;
import com.bionic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author taras.yaroshchuk
 */
@Service
@Async
@PropertySource("classpath:mail.properties")
public class MailServiceImpl implements MailService {

    @Autowired
    private MailSender mailSender;

    @Autowired
    private UserService userService;

    @Autowired
    private UserKeyDao userKeyDao;

    @Resource
    private Environment env;
    public static final String URL = "config.url";


    public void sendMail(String to, String subject, String msg) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(MailConfig.MAIL_USERNAME);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(msg);
        mailSender.send(message);
    }

    public void sendVerification(String email, long key) {

        StringBuilder url = new StringBuilder();
        url
                .append(env.getProperty(URL))
                .append("/verification?key=")
                .append(key);
        String subject = "Verification of the account";
        String message = "Hello! To verify your account please follow link: \n" + url;
        sendMail(email, subject, message);
    }

    public void sendResetPasswordLink(String email, long key) {
        StringBuilder url = new StringBuilder();
        url
                .append(env.getProperty(URL))
                .append("/password?key=")
                .append(key);
        String subject = "Password reset";
        String message = "Your link to password reset: " + url + " \n";
        sendMail(email, subject, message);

    }

    public void sendTemporaryPassword(String email, String password) {
        String subject = "Password reset";
        String message = "Your new temporary password: " + password + " \n";
        message += "Password is valid for 1 hour.";
        sendMail(email, subject, message);
    }

    public void sendReportLinks(String email, int userId,  int year, int period) {

        StringBuilder overtimeUrl = new StringBuilder();
        overtimeUrl
                .append(env.getProperty(URL))
                .append("/summary/")
                .append(userId)
                .append("/")
                .append(year)
                .append("/")
                .append(period)
                .append("/")
                .append("Overtime.xls");


        String message = "Your link to download Overtime report: " + overtimeUrl + " \n";
        StringBuilder allowancesUrl = new StringBuilder();
        allowancesUrl
                .append(env.getProperty(URL))
                .append("/summary/")
                .append(userId)
                .append("/")
                .append(year)
                .append("/")
                .append(period)
                .append("/")
                .append("Allowances.xls");

        message += "Your link to download Allowances report: " + allowancesUrl + " \n";

        String subject = "Overtime & Allowances reports";
        sendMail(email, subject, message);

    }
}
