package com.example.onlinetherapist.Login;

import java.io.Serializable;

public class Patient implements Serializable {
    private String dob;
    private long height;
    private String password;
    private long sex;
    private String username;
    private long weight;
    private String fcm;

    public Patient(){

    }
    public Patient(String dob, long height, String password, long sex, String username,long weight) {
        this.dob = dob;
        this.height = height;
        this.password = password;
        this.sex = sex;
        this.username = username;
        this.weight= weight;
    }

    public Patient(String dob, long height, String password, long sex, String username, long weight, String fcm) {
        this.dob = dob;
        this.height = height;
        this.password = password;
        this.sex = sex;
        this.username = username;
        this.weight = weight;
        this.fcm = fcm;
    }

    public String getFcm() {return fcm;}

    public void setFcm(String fcm){ this.fcm = fcm;}

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public long getHeight() {
        return height;
    }

    public void setHeight(long height) {
        this.height = height;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getSex() {
        return sex;
    }

    public void setSex(long sex) {
        this.sex = sex;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getWeight() {
        return weight;
    }

    public void setWeight(long weight) {
        this.weight = weight;
    }
}
