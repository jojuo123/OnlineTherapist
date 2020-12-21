package com.example.onlinetherapist.homescreen;

import android.app.Activity;

import com.example.onlinetherapist.FirebaseManagement;

public class HomeInteractor implements IHomeInteractor{

    private FirebaseManagement firebaseManagement;

    public HomeInteractor()
    {
        firebaseManagement = FirebaseManagement.getInstance();
    }

    @Override
    public void SendFCMToken(String uname) {
        firebaseManagement.SendFCMTokenPatient(uname);
    }

    @Override
    public void ViewAppointmentPatient(Activity activity) {
        firebaseManagement.ViewAppointmentPatient(activity);
    }

    @Override
    public boolean Logout(Activity activity, String uname) {
        return firebaseManagement.PatientLogout(activity, uname);
    }
}
