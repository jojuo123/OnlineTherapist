package com.example.onlinetherapist.homescreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.onlinetherapist.FirebaseManagement;
import com.example.onlinetherapist.Login.UI.LoginActivity;
import com.example.onlinetherapist.R;
import com.example.onlinetherapist.appointment.BookAppointmentActivity;
import com.example.onlinetherapist.appointment.BookAppointmentPresenter;
import com.example.onlinetherapist.appointment.ViewAppointmentActivity;
import com.example.onlinetherapist.appointment.therapist.TherapistViewAppointmentActivity;

public class HomeActivity extends AppCompatActivity implements IHomeView {

    IHomePresenter homePresenter;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        username=SavedCurrentUsername();
        homePresenter = new HomePresenter(this);
        SharedPreferences preferences=getSharedPreferences("checkbox",MODE_PRIVATE);
        String checkbox= preferences.getString("remember","");
        if(checkbox.equals("true")){
            SharedPreferences.Editor editor=preferences.edit();
            editor.putString("remember and login","true");
            editor.apply();
        }
        else{
            SharedPreferences.Editor editor=preferences.edit();
            editor.putString("remember and login","false");
            editor.apply();
        }

        InitVariable();
        onBookAppointmentClicked();
        onViewAppointmentClicked();

        //Logout();
        //testing: delete when release
        //FirebaseManagement.getInstance().ViewAppointmentPatient(this);
    }
    private void onBookAppointmentClicked()
    {
        Button bookAppointment = findViewById(R.id.book_appointment);
        bookAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent k = new Intent(HomeActivity.this, BookAppointmentActivity.class);
                    //Intent k = new Intent(HomeActivity.this, TherapistViewAppointmentActivity.class);
                    startActivity(k);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private void onViewAppointmentClicked() {
        SharedPreferences savedUsername = getSharedPreferences("SavedUsername", MODE_PRIVATE);
        final String uname = savedUsername.getString("username", "");

        Button viewApp = findViewById(R.id.patient_view_app);
        viewApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homePresenter.onClickAppointment(HomeActivity.this,
                        ViewAppointmentActivity.class, uname, "");
            }
        });
    }

    private void InitVariable() {
        homePresenter = new HomePresenter(this);

        SharedPreferences preferences = getSharedPreferences("SavedFCMToken", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("fcm_token_value",getIntent().getStringExtra("fcm_token"));
        editor.apply();
    }

    private void SendFCMTokenToDatabase() {
        SharedPreferences savedUsername = getSharedPreferences("SavedUsername", MODE_PRIVATE);
        String uname = savedUsername.getString("username", "");
        if (uname != "")
        {
            homePresenter.SendFCMToken(uname);
        }
    }

    public String SavedCurrentUsername(){
        String Username;
        SharedPreferences saved=getSharedPreferences("SavedUsername", MODE_PRIVATE);
        Username = saved.getString("username","");
        return Username;
    }

    public void Logout()
    {
        SharedPreferences savedUsername = getSharedPreferences("SavedUsername", MODE_PRIVATE);
        String uname = savedUsername.getString("username", "");
        if (uname != "")
        {
            homePresenter.Logout(HomeActivity.this, username);
        }
    }

    public void Logout(View view) {
        Logout();
    }

    @Override
    public void onFailed(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}