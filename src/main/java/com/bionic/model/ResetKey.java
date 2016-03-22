package com.bionic.model;

import javax.persistence.*;

/**
 * author Dima Budko
 * v.0.1
 */

@Entity
@Table(name="reset_keys")
public class ResetKey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private long secret;
    private String email;

    public ResetKey() {
    }

    public ResetKey(long secret, String email) {
        this.secret = secret;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public long getSecret() {
        return secret;
    }

    public void setSecret(long secret) {
        this.secret = secret;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
