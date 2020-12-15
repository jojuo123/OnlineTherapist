package com.example.onlinetherapist.videocall;

import com.example.onlinetherapist.Login.Admin;

public interface PatientListener {
    void initiateVideoMeetingToTherapist(Admin admin);
    void initiateAudioMeetingToTherapist(Admin admin);
}
