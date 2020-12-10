package com.example.onlinetherapist.account;

import android.app.Activity;

import java.util.Calendar;

public interface IRegisterPresenter {
    interface Presenter{
        void register(Activity activity, String username, String password, String cf_password,
                      int sex, int height, int weight,
                      Calendar dob);
    }
    interface View{
        void onRegisterFailed(String message);
        void onRegisterSuccess(String message);
    }
    interface Interactor{
        void firebase_registration(Activity activity, String username, String password);
        void validate_infor(Activity activity, String username, String password, String cf_password,
                            int sex, int height, int weight, Calendar dob);
        void upload_infor(Activity activity, String username, String password, int sex,
                          int height, int weight, String dob);
    }
    interface onRegistrationListener{
        void onSuccess(String message);
        void onFailed(String message);
    }
}
