package com.bionic.model;

import javax.persistence.*;

/**
 * author Dima Budko
 * v.0.1
 */

@Entity
@Table(name = "user_keys")
public class UserKey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Long secret;
    @Column(name = "key_type")
    private String keyType;
    private String email;

    public UserKey() {
    }

    public UserKey(long secret, String email, String keyType) {
        this.keyType = keyType;
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

    public String getKeyType() {
        return keyType;
    }

    public void setKeyType(String keyType) {
        this.keyType = keyType;
    }

    @Override
    public String toString() {
        return "UserKey{" +
                "id=" + id +
                ", secret=" + secret +
                ", key='" + keyType + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

}
