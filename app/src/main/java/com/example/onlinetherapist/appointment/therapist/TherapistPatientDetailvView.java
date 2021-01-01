package com.example.onlinetherapist.appointment.therapist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageButton;
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

import org.w3c.dom.Text;

import java.util.ArrayList;

public class TherapistPatientDetailvView extends AppCompatActivity {

    Patient patient;
    TextView dob ;
    TextView username ;
    TextView height;
    TextView weight ;
    TextView sex ;
    ImageButton videoCallButton;
    ImageButton cancelButton;
    ImageButton sendNoteButton;
    TherapistPatientDetailViewPresenter therapistPatientDetailViewPresenter;
    VideoCallPresenter videoCallPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_therapist_patient_detail_view);
        therapistPatientDetailViewPresenter = new TherapistPatientDetailViewPresenter(this);
        videoCallPresenter = new VideoCallPresenter();
        init();



    }

    public void init() {
        patient = (Patient) getIntent().getSerializableExtra("Patient");
        String Date = (String) getIntent().getSerializableExtra("Date");
        dob = findViewById(R.id.dob);
        username = findViewById(R.id.username);
        height = findViewById(R.id.user_height);
        weight = findViewById(R.id.user_weight);
        sex = findViewById(R.id.sex);
        username.setText(patient.getUsername());
        dob.setText(patient.getDob());
        sex.setText(String.valueOf(patient.getSex()));
        height.setText(String.valueOf(patient.getHeight()));
        weight.setText(String.valueOf(patient.getWeight()));


        videoCallButton = findViewById(R.id.VideoCallButton);
        sendNoteButton = findViewById(R.id.Sendnotes_btn);
        AlphaAnimation buttonClick = new AlphaAnimation(0.5f, 1F);
        buttonClick.setDuration(300);

        cancelButton = findViewById(R.id.CancelButton);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(buttonClick);
                //video call here
                if(patient !=null) {
                    if( therapistPatientDetailViewPresenter.cancelAppointment(patient.getUsername(),Date) ==1)
                        patient = null;

                }
                else Toast.makeText(TherapistPatientDetailvView.this,"Patient null, please try again",Toast.LENGTH_SHORT).show();

            }
        });

//        videoCallButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                v.startAnimation(buttonClick);
//                //video call here
//                if (patient != null)
//                    //therapistPatientDetailViewPresenter.videoCall(patient.getUsername(), patient.getFcm());
//                    videoCallPresenter.VideoCallToPatient((Activity) getApplicationContext(), patient.getUsername(), patient.getFcm());
//                else
//                    Toast.makeText(TherapistPatientDetailvView.this,"Patient is not available, please try again",Toast.LENGTH_SHORT).show();
//            }
//        });
        
        sendNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(buttonClick);
                if (patient != null)
                    therapistPatientDetailViewPresenter.sendNote(patient.getUsername());
                else
                    Toast.makeText(TherapistPatientDetailvView.this,"Patient null, please try again",Toast.LENGTH_SHORT).show();
            }
        });
    }
/*
    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
*/
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this,TherapistViewAppointmentActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void VideoCallPatient(View v)
    {
        AlphaAnimation buttonClick = new AlphaAnimation(0.5f, 1F);
        buttonClick.setDuration(300);
        v.startAnimation(buttonClick);
        //video call here
        if (patient != null)
            //therapistPatientDetailViewPresenter.videoCall(patient.getUsername(), patient.getFcm());
            videoCallPresenter.VideoCallToPatient(this, patient.getUsername(), patient.getFcm());
        else
            Toast.makeText(TherapistPatientDetailvView.this,"Patient is not available, please try again",Toast.LENGTH_SHORT).show();
    }

}