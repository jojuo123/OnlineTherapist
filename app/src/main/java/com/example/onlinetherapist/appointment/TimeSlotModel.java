package com.example.onlinetherapist.appointment;

import java.util.Date;

public class TimeSlotModel {
    long slot;
    String date;
    long status;
    public TimeSlotModel(long slot, String date, long status) {
        this.slot = slot;
        this.date = date;
        this.status = status;
    }



}
