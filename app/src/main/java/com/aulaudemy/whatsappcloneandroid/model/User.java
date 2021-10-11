package com.aulaudemy.whatsappcloneandroid.model;

public class User {
    private String name;
    private String phone;
    public String token;

    public User() {
    }

    public User(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
