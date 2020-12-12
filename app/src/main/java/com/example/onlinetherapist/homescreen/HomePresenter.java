package com.example.onlinetherapist.homescreen;

import android.app.Activity;

public class HomePresenter implements IHomePresenter {

    IHomeView homeView;
    IHomeInteractor homeInteractor;

    public HomePresenter(IHomeView view)
    {
        this.homeView = view;
        homeInteractor = new HomeInteractor();
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void SendFCMToken(String uname) {
        homeInteractor.SendFCMToken(uname);
    }

    @Override
    public boolean Logout(Activity activity, String uname) {
        return homeInteractor.Logout(activity, uname);
    }
}
