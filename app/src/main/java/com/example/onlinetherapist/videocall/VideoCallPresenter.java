package com.example.onlinetherapist.videocall;

import android.app.Activity;
import android.content.Intent;

import java.io.InputStream;

public class VideoCallPresenter implements IVideoCallPresenter{

    @Override
    public void VideoCallToTherapist(Activity activity, String adminUName, String adminToken) {
        Intent intent = new Intent(activity.getApplicationContext(), OutgoingActivity.class);
        intent.putExtra("inviteeUName", adminUName);
        intent.putExtra("inviter_token", adminToken);
        intent.putExtra("type", "video");
        activity.startActivity(intent);
    }

    @Override
    public void AudioCallToTherapist(Activity activity, String adminUName, String adminToken) {
        Intent intent = new Intent(activity.getApplicationContext(), OutgoingActivity.class);
        intent.putExtra("inviteeUName", adminUName);
        intent.putExtra("inviter_token", adminToken);
        intent.putExtra("type", "audio");
        activity.startActivity(intent);
    }

    @Override
    public void VideoCallToPatient(Activity activity, String patientUName, String patientToken) {
        Intent intent = new Intent(activity.getApplicationContext(), OutgoingActivity.class);
        intent.putExtra("inviteeUName", patientUName);
        intent.putExtra("inviter_token", patientToken);
        intent.putExtra("type", "video");
        activity.startActivity(intent);
    }

    @Override
    public void AudioCallToPatient(Activity activity, String patientUName, String patientToken) {
        Intent intent = new Intent(activity.getApplicationContext(), OutgoingActivity.class);
        intent.putExtra("inviteeUName", patientUName);
        intent.putExtra("inviter_token", patientToken);
        intent.putExtra("type", "audio");
        activity.startActivity(intent);
    }
}
