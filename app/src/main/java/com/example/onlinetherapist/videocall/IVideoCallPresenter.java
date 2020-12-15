package com.example.onlinetherapist.videocall;

import android.app.Activity;

public interface IVideoCallPresenter {
    void VideoCallToTherapist(Activity activity, String adminUName, String adminToken);
    void AudioCallToTherapist(Activity activity, String adminUName, String adminToken);
    void VideoCallToPatient(Activity activity, String patientUName, String patientToken);
    void AudioCallToPatient(Activity activity, String patientUName, String patientToken);
}
