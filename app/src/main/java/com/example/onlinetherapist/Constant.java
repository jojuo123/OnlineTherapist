package com.example.onlinetherapist;

import java.util.HashMap;

public class Constant {
    public static final String PATIENT_TABLE = "Patients";
    public static final String ANY_USERNAME = "username";
    public static final String APPOINTMENT_TABLE = "Appointment";
    public static final String TODOLIST_TABLE = "Todolist";
    public static final String TODOLISTITEM_TABLE = "Todolist_item";
    public static final String NOTE_TABLE = "Note";

    public static final String THERAPIST_UNAME = "therapist1";

    public static final String FCM_TOKEN = "fcm";
    public static final String THERAPIST_TABLE = "Therapists";


    public static final String REMOTE_MSG_AUTHORIZATION = "Authorization";
    public static final String REMOTE_MSG_CONTENT_TYPE = "Content-Type";
    //public static String FCM_TOKEN_STRING = "";

    public static final String REMOTE_MSG_TYPE = "type";
    public static final String REMOTE_MSG_INVITATION = "invitation";
    public static final String REMOTE_MSG_MEETING_TYPE = "meetingType";
    public static final String REMOTE_MSG_INVITER_TOKEN = "inviterToken";
    public static final String REMOTE_MSG_DATA = "data";
    public static final String REMOTE_MSG_REGISTRATION_IDS = "registration_ids";

    public static HashMap<String, String> getRemoteMessageHeader()
    {
        HashMap<String, String> headers = new HashMap<>();
        headers.put(
          Constant.REMOTE_MSG_AUTHORIZATION,
          "AAAAj2tdjQk:APA91bGzMHxXR3kAW7Gqqq39qavg_qRVu679TYa5dHbncrhrX90ImE5t1J8WMP_r_Ulnq-hikGsIMDZ1Y7WdbbkTNgF2np_yQc8jwNRwC8MvHfuoXGVtbkqX5TDsgSGc1Xg0mv8H9-rH"
        );
        headers.put(Constant.REMOTE_MSG_CONTENT_TYPE, "application/json");
        return headers;
    }
}
