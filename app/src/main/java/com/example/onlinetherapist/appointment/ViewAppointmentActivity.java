package com.example.onlinetherapist.appointment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.onlinetherapist.Login.Admin;
import com.example.onlinetherapist.R;
import com.example.onlinetherapist.videocall.IVideoCallPresenter;
import com.example.onlinetherapist.videocall.PatientListener;
import com.example.onlinetherapist.videocall.VideoCallPresenter;

public class ViewAppointmentActivity extends AppCompatActivity implements IViewAppointmentView, PatientListener {

    IVideoCallPresenter videoCallPresenter;
    Admin therapist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_appointment);
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
}