package com.example.onlinetherapist.account;

import android.app.Activity;

import com.google.firebase.database.DatabaseReference;

import java.util.Calendar;

public class RegisterPresenter implements IRegisterPresenter.Presenter, IRegisterPresenter.onRegistrationListener {
    private IRegisterPresenter.View register_view;
    private RegisterInteractor interactor;
    public RegisterPresenter(IRegisterPresenter.View view){
        register_view = view;
        interactor = new RegisterInteractor(this);
    }

    @Override
    public void register(Activity activity, String username, String password, String cf_password,
                         int sex, int height, int weight, Calendar dob) {
        interactor.validate_infor(activity, username, password,
                cf_password, sex, height, weight, dob);
    }

    @Override
    public void onFailed(String message) {
        register_view.onRegisterFailed(message);
    }

    @Override
    public void onSuccess(String message) {
        register_view.onRegisterSuccess(message);
    }
}
