package com.example.onlinetherapist.homescreen.therapist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.onlinetherapist.Activities.Home;
import com.example.onlinetherapist.Constant;
import com.example.onlinetherapist.R;

import com.example.onlinetherapist.videocall.OutgoingActivity;



import java.net.MalformedURLException;
import java.net.URL;
import com.example.onlinetherapist.appointment.therapist.TherapistViewAppointmentActivity;
import com.example.onlinetherapist.homescreen.HomeActivity;
import com.example.onlinetherapist.homescreen.HomePresenter;

public class TherapistHomeActivity extends AppCompatActivity implements ITherapistHomeView{
    String username;
    ITherapistHomePresenter therapistHomePresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_therapist_home);
        username = TherapistSavedCurrentUsername();
        therapistHomePresenter = new TherapistHomePresenter(this);

//        checkRememberLogin();

        InitVariable();
        Constant.isTherapist = true;
        startActivity(new Intent(TherapistHomeActivity.this, Home.class));
        finish();

//        checkRememberLogin();
//
//        onLogoutClicked();
    }

    private void onLogoutClicked() {
        final Button logout = findViewById(R.id.therapist_logout);
        logout.setOnClickListener(view -> logoutCall());
    }

    private void logoutCall() {
        SharedPreferences savedUsername = getSharedPreferences("SavedUsername", MODE_PRIVATE);
        String uname = savedUsername.getString("username", "");
        if (uname != "")
        {
            therapistHomePresenter.Logout(TherapistHomeActivity.this, username);
        }
    }


    private void InitVariable() {
        //therapistHomePresenter = new HomePresenter(this);

        SharedPreferences preferences = getSharedPreferences("SavedFCMToken", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("fcm_token_value",getIntent().getStringExtra("fcm_token"));
        editor.apply();

        ((Button)this.findViewById(R.id.therapist_view_table)).setOnClickListener(v -> {
            Intent intent = new Intent(this.getApplicationContext(), TherapistViewAppointmentActivity.class);
            startActivity(intent);
        });
    }

//    private void checkRememberLogin() {
//        SharedPreferences preferences=getSharedPreferences("checkbox",MODE_PRIVATE);
//        String checkbox= preferences.getString("remember","");
//        if(checkbox.equals("true")){
//            SharedPreferences.Editor editor=preferences.edit();
//            editor.putString("remember and login","true");
//            editor.apply();
//        }
//        else{
//            SharedPreferences.Editor editor=preferences.edit();
//            editor.putString("remember and login","false");
//            editor.apply();
//        }
//    }

    public String TherapistSavedCurrentUsername(){
        String Username;
        SharedPreferences saved=getSharedPreferences("SavedUsername", MODE_PRIVATE);
        Username = saved.getString("username","");
        return Username;
    }
    public void onViewAppointmentClicked(){
        therapistHomePresenter.onClickAppointment(this,
                TherapistViewAppointmentActivity.class, "", "");
    }
}