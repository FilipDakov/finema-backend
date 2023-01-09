package com.fd.finema.security;

import com.fd.finema.bom.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="user")
public class User extends BaseEntity {

    @Column(unique = true)
    private String email;

    private String password;

    private Boolean isAdmin;

    private String passResetToken;

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String email, String password, Boolean isAdmin) {
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public User() {

    }

    public String getPassResetToken() {
        return passResetToken;
    }

    public void setPassResetToken(String passResetToken) {
        this.passResetToken = passResetToken;
    }
}
