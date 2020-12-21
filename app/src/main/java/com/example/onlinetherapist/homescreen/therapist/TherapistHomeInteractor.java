package com.example.onlinetherapist.homescreen.therapist;

import android.app.Activity;

import com.example.onlinetherapist.FirebaseManagement;

public class TherapistHomeInteractor implements ITherapistHomeInteractor{
    private FirebaseManagement firebaseManagement;

    public TherapistHomeInteractor()
    {
        firebaseManagement = FirebaseManagement.getInstance();
    }

    @Override
    public void SendFCMToken(String uname) {
        firebaseManagement.SendFCMTokenPatient(uname);
    }

    @Override
    public boolean Logout(Activity activity, String uname) {
        return firebaseManagement.PatientLogout(activity, uname);
    }
}
