package com.example.onlinetherapist.homescreen.therapist;

import android.app.Activity;

public interface ITherapistHomeInteractor {
    void SendFCMToken(String uname);

    void Logout(Activity activity, String uname);
}
