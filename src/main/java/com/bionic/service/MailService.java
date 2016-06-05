package com.bionic.service;

import com.bionic.exception.auth.impl.UserNotExistsException;

/**
 * @author taras.yaroshchuk
 */

public interface MailService {

    void sendMail(String to, String subject, String msg);

    void sendVerification(String email, long key);

    void sendResetPasswordLink(String email, long key);

    void sendTemporaryPassword(String email, String password);

    void sendReportLinks(String email, int userId, int year, int period) throws UserNotExistsException;
}
