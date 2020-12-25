package com.example.onlinetherapist;

import com.example.onlinetherapist.Login.Patient;
import com.example.onlinetherapist.appointment.TimeRowModel;
import com.example.onlinetherapist.appointment.TimeSlotModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

public interface onReadDataListener {
    void onStart();
    void onSuccess(DataSnapshot data, String message);
    void onSuccessTimeSlot(ArrayList<TimeSlotModel> timeSlotModels);
    void onSuccessAddNewAppointment();
    void onSuccessGetPatientInfo(Patient patient);
    void onFailed(DatabaseError e);
}
