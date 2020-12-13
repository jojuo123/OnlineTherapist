package com.example.onlinetherapist.homescreen;

import android.app.Activity;

public interface IHomeInteractor {
    void SendFCMToken(String uname);

    boolean Logout(Activity activity, String uname);
}
