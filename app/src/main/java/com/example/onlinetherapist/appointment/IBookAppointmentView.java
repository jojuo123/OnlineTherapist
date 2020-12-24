package com.example.onlinetherapist.appointment;

import java.util.ArrayList;

public interface IBookAppointmentView {



    void initAvailableTimeTable();
    void bookAppointment(String date, int slot);

}
