package com.example.onlinetherapist.homescreen.therapist;

import android.app.Activity;

public interface ITherapistHomeInteractor {
    void SendFCMToken(String uname);

    boolean Logout(Activity activity, String uname);
}
