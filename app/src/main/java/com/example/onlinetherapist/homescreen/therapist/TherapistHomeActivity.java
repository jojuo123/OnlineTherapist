package com.example.onlinetherapist.homescreen.therapist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.onlinetherapist.R;
import org.jitsi.meet.*;
import com.example.onlinetherapist.videocall.OutgoingActivity;

import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;

public class TherapistHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_therapist_home);
    }
}