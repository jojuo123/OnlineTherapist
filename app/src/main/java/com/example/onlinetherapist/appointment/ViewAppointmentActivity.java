package com.example.onlinetherapist.appointment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlinetherapist.Login.Admin;
import com.example.onlinetherapist.R;
import com.example.onlinetherapist.videocall.IVideoCallPresenter;
import com.example.onlinetherapist.videocall.PatientListener;
import com.example.onlinetherapist.videocall.VideoCallPresenter;


public class ViewAppointmentActivity extends AppCompatActivity implements IViewAppointmentView, PatientListener {

    IVideoCallPresenter videoCallPresenter;
    Admin therapist;
    TextView timeAppointment, dateAppointment, statusAppointment;
    ImageButton cancelAppointment;
    ViewAppointmentPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_appointment);
      presenter = new ViewAppointmentPresenter((IViewAppointmentView) this);
        displayAppointment();
        onCancelClicked();
        initVariable();
        initButton();
    }

    private void initButton() {
        this.findViewById(R.id.btnVidCallTherapist).setOnClickListener(view -> initiateVideoMeetingToTherapist(therapist));

        this.findViewById(R.id.btnAudioCallTherapist).setOnClickListener(view -> initiateAudioMeetingToTherapist(therapist));
    }

    private void initVariable() {
        String name = getIntent().getStringExtra("therapistname");
        String fcm = getIntent().getStringExtra("therapistfcm");
        therapist = new Admin("", name);
        therapist.setFcm(fcm);

        videoCallPresenter = new VideoCallPresenter();
    }

    @Override
    public void initiateVideoMeetingToTherapist(Admin admin) {
        if (admin.getFcm() == null || admin.getFcm().trim().isEmpty())
        {
            Toast.makeText(this, "Therapist is not available", Toast.LENGTH_SHORT).show();
        }
        else
        {
            videoCallPresenter.VideoCallToTherapist(this, admin.getUsername(), admin.getFcm());
        }
    }

    @Override
    public void initiateAudioMeetingToTherapist(Admin admin) {
        if (admin.getFcm() == null || admin.getFcm().trim().isEmpty())
        {
            Toast.makeText(this, "Therapist is not available", Toast.LENGTH_SHORT).show();
        }
        else
        {
            videoCallPresenter.AudioCallToTherapist(this, admin.getUsername(), admin.getFcm());
        }
    }

    private void refresh() {
        finish();
        startActivity(getIntent());
    }

    public void displayAppointment(){
        timeAppointment = findViewById(R.id.time_appointment);
        dateAppointment = findViewById(R.id.date_appointment);
        statusAppointment = findViewById(R.id.status_appointment);

        presenter.retrieveAppointment(this, timeAppointment, dateAppointment);

    }
    public void onCancelClicked(){

        cancelAppointment = findViewById(R.id.cancel_appointment);
        cancelAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.cancellation(ViewAppointmentActivity.this);

            }
        });
    }
    @Override
    public void onFailCancel(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccessCancel(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}