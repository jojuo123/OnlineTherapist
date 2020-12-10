package com.example.onlinetherapist.appointment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.onlinetherapist.R;

public class ViewAppointmentActivity extends AppCompatActivity implements IViewAppointmentView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_appointment);
    }
}