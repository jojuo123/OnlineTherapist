package com.example.onlinetherapist.videocall;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.onlinetherapist.R;

public class VideoCallActivity extends AppCompatActivity implements IVideoCallView{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_call);
    }
}