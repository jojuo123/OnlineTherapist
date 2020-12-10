package com.example.onlinetherapist.homescreen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.onlinetherapist.R;

public class HomeActivity extends AppCompatActivity implements IHomeView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }
}