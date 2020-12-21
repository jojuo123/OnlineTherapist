package com.example.onlinetherapist.homescreen;

import android.app.Activity;

import com.example.onlinetherapist.appointment.BookAppointmentActivity;
import com.example.onlinetherapist.appointment.ViewAppointmentActivity;

public interface IHomePresenter {
    void onPause();
    void onResume();
    void onCreate();
    void onDestroy();

    void SendFCMToken(String uname);
    boolean Logout(Activity activity, String uname);
    void onClickAppointment(Activity current, Class<ViewAppointmentActivity> viewApp, String username, String fcm);
    void onClickNotes(Activity current, Activity viewNotes);
    void onClickAdivces(Activity current, Activity viewAdvices);
    void onClickBookAppointment(Activity current, Class<BookAppointmentActivity> bookAppointment);
}
