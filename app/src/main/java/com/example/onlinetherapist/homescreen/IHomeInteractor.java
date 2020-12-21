package com.example.onlinetherapist.homescreen;

import android.app.Activity;

public interface IHomeInteractor {
    void SendFCMToken(String uname);
    void ViewAppointmentPatient(final Activity activity);
    boolean Logout(Activity activity, String uname);
}
