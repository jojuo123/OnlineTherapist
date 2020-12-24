package com.example.onlinetherapist.appointment.therapist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.TextView;

import com.example.onlinetherapist.Login.Patient;
import com.example.onlinetherapist.R;

import org.w3c.dom.Text;

public class TherapistPatientDetailvView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_therapist_patient_detail_view);
        Patient patient = (Patient) getIntent().getSerializableExtra("Patient");

        TextView dob = findViewById(R.id.dob);
        TextView username = findViewById(R.id.username);
        TextView height = findViewById(R.id.user_height);
        TextView weight = findViewById(R.id.user_weight);
        TextView sex = findViewById(R.id.sex);
        username.setText("Profile of patient: "+patient.getUsername());
        dob.setText("Date of birth: "+patient.getDob());
        sex.setText("Sex: "+patient.getSex());
        height.setText("Height: "+patient.getHeight());
        weight.setText("Weight: "+patient.getWeight());


        Button videoCallButton = findViewById(R.id.VideoCallButton);
        videoCallButton.setBackground(ContextCompat.getDrawable(this,R.drawable.your_booked_button));
        videoCallButton.setGravity(Gravity.CENTER_HORIZONTAL);
        AlphaAnimation buttonClick = new AlphaAnimation(0.5f, 1F);
        buttonClick.setDuration(300);
        videoCallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(buttonClick);
                //video call here
                patient.getFcm();
            }
        });





    }
}