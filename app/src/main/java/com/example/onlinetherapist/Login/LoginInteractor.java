package com.example.onlinetherapist.Login;

import android.app.Activity;

import com.example.onlinetherapist.FirebaseManagement;

public class LoginInteractor implements ILoginPresenter.Interactor {
    private FirebaseManagement firebaseManagement;
    public LoginInteractor(){
        firebaseManagement=FirebaseManagement.getInstance();
    }
    @Override
    public void doSignin(Activity activity, String username, String password) {
        firebaseManagement.doSignIn(activity,username,password);
    }

    @Override
    public void doSigninAdmin(Activity activity, String username, String password) {
        firebaseManagement.doSignInAdmin(activity, username,password);
    }

    @Override
    public void navigateToMainMenu(Activity loginActivity, String username) {
        //firebaseManagement.
    }
}
