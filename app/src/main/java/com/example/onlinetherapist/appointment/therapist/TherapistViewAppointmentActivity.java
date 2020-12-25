package com.example.onlinetherapist.appointment.therapist;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


import com.example.onlinetherapist.FirebaseManagement;
import com.example.onlinetherapist.R;
import com.example.onlinetherapist.appointment.TimeRowModel;

import java.util.ArrayList;

public class TherapistViewAppointmentActivity extends AppCompatActivity implements ITherapistViewAppointmentView{

    TextView textView;
    Button button;
    TherapistViewAppointmentPresenter therapistViewAppointmentPresenter = new TherapistViewAppointmentPresenter(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_therapist_view_appointment);
        therapistViewAppointmentPresenter.queryTimeSlotData();

    }
}