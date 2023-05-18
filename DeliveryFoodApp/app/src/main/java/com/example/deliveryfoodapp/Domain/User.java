package com.example.deliveryfoodapp.Domain;

public class User {
    public String fullname, phone, email, password, avatar;

    public User() {

    }

    public User(String fullname, String phone, String email) {
        this.fullname = fullname;
        this.phone = phone;
        this.email = email;
    }

    public User(String fullname, String phone, String email, String password) {
        this.fullname = fullname;
        this.phone = phone;
        this.email = email;
        this.password = password;
    }

    public User(String fullname, String phone, String email, String password, String avatar) {
        this.fullname = fullname;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.avatar = avatar;
    }

    public User(String avatar) {
        this.avatar = avatar;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
