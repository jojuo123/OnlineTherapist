package com.example.onlinetherapist.appointment;



public class TimeRowModel{
    private String date;
    private int morningAvailable;
    private int eveningAvailable;


    public TimeRowModel(String date, int morningAvailable, int eveningAvailable) {
        this.date = date;
        this.morningAvailable = morningAvailable;
        this.eveningAvailable = eveningAvailable;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int isMorningAvailable() {
        return morningAvailable;
    }

    public void setMorningAvailable(int morningAvailable) {
        this.morningAvailable = morningAvailable;
    }

    public int isEveningAvailable() {
        return eveningAvailable;
    }

    public void setEveningAvailable(int eveningAvailable) {
        this.eveningAvailable = eveningAvailable;
    }
}
