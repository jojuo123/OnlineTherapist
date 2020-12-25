package com.example.onlinetherapist.Login;

import android.app.Activity;

public class LoginPresenter implements ILoginPresenter.Presenter {
    private LoginInteractor interactor;

    public LoginPresenter() {
        interactor= new LoginInteractor();
    }
    @Override
    public void authorize(Activity activity, String username, String password) {
        interactor.doSignin(activity,username,password);
    }

    @Override
    public void Adminauthorize(Activity activity, String username, String password) {
        interactor.doSigninAdmin(activity,username,password);
    }

    @Override
    public void navigateToMainMenu(Activity loginActivity, String username) {
        interactor.navigateToMainMenu(loginActivity, username);
    }
}
