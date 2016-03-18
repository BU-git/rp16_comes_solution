package com.bionic.model.forms;

/**
 * @author Pavel Boiko
 */
public class PasswordReset {

    private String email;

    public PasswordReset() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "PasswordReset{" +
                "email='" + email + '\'' +
                '}';
    }
}
