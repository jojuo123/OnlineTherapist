package com.example.onlinetherapist.appointment;

import android.app.Activity;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public interface IViewAppointmentPresenter {
    public interface Presenter{
        void displayPatientAppointment(Activity activity, TextView timeAppointment,
                                       TextView dateAppointment);
        void cancellation(Activity activity);
        void retrieveAppointment(Activity activity, TextView timeAppointment,
                                 TextView dateAppointment);
    }

}
