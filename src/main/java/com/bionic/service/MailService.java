package com.bionic.service;

/**
 * @author taras.yaroshchuk
 */

public interface MailService {

    void sendMail(String to, String subject, String msg);

    void sendVerification(String email, long key);

    void sendResetPasswordLink(String email, long key);

    void sendTemporaryPassword(String email, String password);

    void sendPeriodReportLink(String email,int period,int year);
}
