package com.example.onlinetherapist.Login;

public class Admin {
    private String username;
    private String password;

    public Admin(){

    }

    public Admin(String password, String username) {
        this.username = username;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}