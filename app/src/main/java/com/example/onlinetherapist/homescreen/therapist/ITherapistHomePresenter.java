package com.example.onlinetherapist.homescreen.therapist;

import android.app.Activity;

import com.example.onlinetherapist.appointment.therapist.TherapistViewAppointmentActivity;

public interface ITherapistHomePresenter {
    void SendFCMToken(String uname);
    void Logout(Activity activity, String uname);
    void onClickAppointment(Activity current, Class<TherapistViewAppointmentActivity> viewApp, String username, String fcm);

}
