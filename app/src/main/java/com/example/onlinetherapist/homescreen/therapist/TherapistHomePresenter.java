package com.example.onlinetherapist.homescreen.therapist;

import android.app.Activity;
import android.content.Intent;

import com.example.onlinetherapist.appointment.therapist.TherapistViewAppointmentActivity;


public class TherapistHomePresenter implements ITherapistHomePresenter {
    ITherapistHomeView view;
    ITherapistHomeInteractor interactor;
    public TherapistHomePresenter(ITherapistHomeView view)
    {
        this.view = view;
        interactor = new TherapistHomeInteractor();
    }

    @Override
    public void SendFCMToken(String uname) {
        interactor.SendFCMToken(uname);
    }

    @Override
    public void Logout(Activity activity, String uname) {
        interactor.Logout(activity, uname);
    }

    @Override
    public void onClickAppointment(Activity current, Class<TherapistViewAppointmentActivity> viewApp, String username, String fcm) {
        Intent intent = new Intent(current, viewApp);
        intent.putExtra("username", username);
        intent.putExtra("fcm", fcm);
        current.startActivity(intent);
    }


}
