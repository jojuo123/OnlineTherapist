package com.example.onlinetherapist.homescreen;

import android.app.Activity;

public interface IHomePresenter {
    void onPause();
    void onResume();
    void onCreate();
    void onDestroy();

    void SendFCMToken(String uname);
    boolean Logout(Activity activity, String uname);
}
