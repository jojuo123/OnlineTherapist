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


    // MEDICAL RECORD TABLE on Firebase
    public static final String MEDICAL_RECORD_TABLE = "Medical_record";
    public static final String RECORD_ID = "ID";
    public static final String RECORD_PATIENT_ID = "PatientID";
    public static final String RECORD_THERAPIST_ID = "TherapistID";
    public static final String RECORD_DATE = "Date";
    public static final String RECORD_PROBLEM = "Problem";
    public static final String RECORD_DIAGNOSIS = "Diagnosis";
    public static final String RECORD_TREATMENT = "Treatment";
    public static final String RECORD_NOTE_ID = "NoteID";
    public static final String RECORD_TODOLIST_ID = "TodoListID";




    public static final String REMOTE_MSG_AUTHORIZATION = "Authorization";
    public static final String REMOTE_MSG_CONTENT_TYPE = "Content-Type";
    //public static String FCM_TOKEN_STRING = "";

    public static final String REMOTE_MSG_TYPE = "type";
    public static final String REMOTE_MSG_INVITATION = "invitation";
    public static final String REMOTE_MSG_MEETING_TYPE = "meetingType";
    public static final String REMOTE_MSG_INVITER_TOKEN = "inviterToken";
    public static final String REMOTE_MSG_DATA = "data";
    public static final String REMOTE_MSG_REGISTRATION_IDS = "registration_ids";

    public static final String REMOTE_MSG_INVITATION_RESPONSE = "invitationResponse";
    public static final String REMOTE_MSG_INVITATION_ACCEPTED = "accepted";
    public static final String REMOTE_MSG_INVITATION_REJECTED = "rejected";
    public static final String REMOTE_MSG_INVITATION_CANCEL = "cancelled";

    public static final String REMOTE_MSG_MEETING_ROOM = "meetingRoom";

    public static boolean isTherapist = false;

    public static HashMap<String, String> getRemoteMessageHeader()
    {
        HashMap<String, String> headers = new HashMap<>();
        headers.put(
          Constant.REMOTE_MSG_AUTHORIZATION,
          "key=AAAAj2tdjQk:APA91bHzkeZY94wfxv7Z-q597fglwrwN2oYRAbe4CKVl0tKewU9U8ajHm3EyJD91zM7A6pnhEPWkd0J_WTbRdv6tk7TrOyaAZiIOcN3-A_SZ0ukWKHudTcyUl6Me28SCcvUjSG9kRK1h"
        );
        headers.put(Constant.REMOTE_MSG_CONTENT_TYPE, "application/json");
        return headers;
    }

    //public static String FCM_TOKEN_VALUE = null;
}
