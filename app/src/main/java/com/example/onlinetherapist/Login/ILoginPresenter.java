package com.example.onlinetherapist.Login;

import android.app.Activity;

public interface ILoginPresenter {
    interface Presenter{
        void authorize(Activity activity, String username, String password);
        void Adminauthorize(Activity activity,String username, String password);
    }
    interface View{

    }
    interface Interactor{
        void doSignin(Activity activity,String username, String password);
        void doSigninAdmin(Activity activity,String username, String password);
    }
}
