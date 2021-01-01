package com.example.onlinetherapist.appointment.therapist;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlinetherapist.FirebaseManagement;
import com.example.onlinetherapist.Login.Patient;
import com.example.onlinetherapist.R;
import com.example.onlinetherapist.appointment.TimeSlotModel;
import com.example.onlinetherapist.onReadDataListener;
import com.example.onlinetherapist.videocall.VideoCallPresenter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

public class TherapistPatientDetailViewPresenter {
    FirebaseManagement firebaseManagement;
    Activity activity;


    public TherapistPatientDetailViewPresenter(Activity activity)
    {
        firebaseManagement = FirebaseManagement.getInstance();
        this.activity = activity;
    }
    public void videoCall(String userName, String fcm)
    {
    }
    public void cancelAppointment(String username)
    {
        DialogInterface.OnClickListener dialog = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case DialogInterface.BUTTON_POSITIVE:
                        firebaseManagement.cancelAppointment(username, new onReadDataListener() {
                            @Override
                            public void onStart() {

                            }

                            @Override
                            public void onSuccess(DataSnapshot data, String message) {
                                TextView dob = activity.findViewById(R.id.dob);
                                TextView username =activity.findViewById(R.id.username);
                                TextView height = activity.findViewById(R.id.user_height);
                                TextView weight =activity.findViewById(R.id.user_weight);
                                TextView sex =activity.findViewById(R.id.sex);
                                username.setText("Profile of patient: ");
                                dob.setText("Date of birth: ");
                                sex.setText("Sex: ");
                                height.setText("Height: ");
                                weight.setText("Weight: ");
                                Toast.makeText(activity,"Cancel appoinment succesful",Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onSuccessTimeSlot(ArrayList<TimeSlotModel> timeSlotModels) {

                            }

                            @Override
                            public void onSuccessAddNewAppointment() {

                            }

                            @Override
                            public void onSuccessGetPatientInfo(Patient patient) {

                            }

                            @Override
                            public void onFailed(DatabaseError e) {
                                Toast.makeText(activity,"Cancel appoinment fail, please try again",Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
        alertDialog.setTitle("Confirm Cancellation");
        alertDialog.setMessage("Are you sure to cancel the appointment?");
        alertDialog.setPositiveButton("Yes",dialog).setNegativeButton("No",dialog).show();
    }
}
