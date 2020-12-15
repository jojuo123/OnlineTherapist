package com.example.onlinetherapist.appointment;

public interface IBookAppointmentView {

    void initAvailableTimeTable();
    void bookAppointment(String date, int slot);
}
