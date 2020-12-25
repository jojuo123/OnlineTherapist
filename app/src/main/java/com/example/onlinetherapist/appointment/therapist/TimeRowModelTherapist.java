package com.example.onlinetherapist.appointment.therapist;

public class TimeRowModelTherapist {
    private String date;
    private String morningBook;
    private String eveningBook;


    public TimeRowModelTherapist(String date, String morningBook, String eveningBook) {
        this.date = date;
        this.morningBook = morningBook;
        this.eveningBook = eveningBook;
    }

    public String getMorningBook() {
        return morningBook;
    }

    public void setMorningBook(String morningBook) {
        this.morningBook = morningBook;
    }

    public String getEveningBook() {
        return eveningBook;
    }

    public void setEveningBook(String eveningBook) {
        this.eveningBook = eveningBook;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}