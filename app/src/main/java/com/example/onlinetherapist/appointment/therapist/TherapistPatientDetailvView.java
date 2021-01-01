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
    Button videoCallButton;
    Button cancelButton;
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
        dob = findViewById(R.id.dob);
        username = findViewById(R.id.username);
        height = findViewById(R.id.user_height);
        weight = findViewById(R.id.user_weight);
        sex = findViewById(R.id.sex);
        username.setText("Profile of patient: "+patient.getUsername());
        dob.setText("Date of birth: "+patient.getDob());
        sex.setText("Sex: "+patient.getSex());
        height.setText("Height: "+patient.getHeight());
        weight.setText("Weight: "+patient.getWeight());


        videoCallButton = findViewById(R.id.VideoCallButton);
        videoCallButton.setBackground(ContextCompat.getDrawable(this,R.drawable.your_booked_button));
        videoCallButton.setGravity(Gravity.CENTER_HORIZONTAL);
        AlphaAnimation buttonClick = new AlphaAnimation(0.5f, 1F);
        buttonClick.setDuration(300);

        cancelButton = findViewById(R.id.CancelButton);
        cancelButton.setBackground(ContextCompat.getDrawable(this,R.drawable.therapist_cancel_button));
        cancelButton.setGravity(Gravity.CENTER_HORIZONTAL);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(buttonClick);
                //video call here
                if(patient !=null) {
                    therapistPatientDetailViewPresenter.cancelAppointment(patient.getUsername());
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
    }
    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this,TherapistViewAppointmentActivity.class);
        startActivity(intent);
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