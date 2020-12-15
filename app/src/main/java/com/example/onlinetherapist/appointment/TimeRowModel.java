package com.example.onlinetherapist.appointment;



public class TimeRowModel{
    private String date;
    private boolean morningAvailable;
    private boolean eveningAvailable;


    public TimeRowModel(String date, boolean morningAvailable, boolean eveningAvailable) {
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

    public boolean isMorningAvailable() {
        return morningAvailable;
    }

    public void setMorningAvailable(boolean morningAvailable) {
        this.morningAvailable = morningAvailable;
    }

    public boolean isEveningAvailable() {
        return eveningAvailable;
    }

    public void setEveningAvailable(boolean eveningAvailable) {
        this.eveningAvailable = eveningAvailable;
    }
}
