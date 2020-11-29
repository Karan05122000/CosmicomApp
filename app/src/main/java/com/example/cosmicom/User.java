package com.example.cosmicom;

public class User {
    private  String email,usertype,password;

    public User(String email, String usertype, String password) {
        this.email = email;
        this.usertype = usertype;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getUsertype() {
        return usertype;
    }

    public String getPassword() {
        return password;
    }
}
