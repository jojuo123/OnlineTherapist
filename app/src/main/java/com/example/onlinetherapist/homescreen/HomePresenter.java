package com.example.onlinetherapist.homescreen;

import android.app.Activity;
import android.content.Intent;

import com.example.onlinetherapist.appointment.ViewAppointmentActivity;

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

    @Override
    public void onClickAppointment(Activity current, Class<ViewAppointmentActivity> viewApp, String username, String fcm) {
        Intent intent = new Intent(current, viewApp);
        intent.putExtra("therapistname", username);
        intent.putExtra("therapistfcm", fcm);
        current.startActivity(intent);
    }

    @Override
    public void onClickNotes(Activity current, Activity viewNotes) {

    }

    @Override
    public void onClickAdivces(Activity current, Activity viewAdvices) {

    }

    @Override
    public void onClickBookAppointment(Activity current, Activity bookAppointment) {

    }

}
