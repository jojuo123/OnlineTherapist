package com.example.onlinetherapist.appointment.therapist;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlinetherapist.Login.Patient;
import com.example.onlinetherapist.MedicalRecord.therapist.CreateMedicalRecordActivity;
import com.example.onlinetherapist.R;
import com.example.onlinetherapist.noteadvice.therapist.note.CreateANoteActivity;
import com.example.onlinetherapist.noteadvice.therapist.todoList.CreateATodolistActivity;
import com.example.onlinetherapist.videocall.VideoCallPresenter;

public class TherapistPatientDetailvView extends AppCompatActivity implements ITherapistPatientDetailViewContract.View{

    Patient patient;
    TextView dob ;
    TextView username ;
    TextView height;
    TextView weight ;
    TextView sex ;
    ImageButton videoCallButton;
    ImageButton cancelButton;
    ImageButton sendNoteButton;
    ImageButton createTodoList;
    ImageButton imgBtnMedicalRecord;
    TherapistPatientDetailViewPresenter presenter;
    VideoCallPresenter videoCallPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_therapist_patient_detail_view);
        presenter = new TherapistPatientDetailViewPresenter(this);
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
        if (patient.getSex() ==1 )
            sex.setText("Male");
        else sex.setText("Female");
        height.setText(String.valueOf(patient.getHeight())+ " cm");
        weight.setText(String.valueOf(patient.getWeight())+ " kg");


        videoCallButton = findViewById(R.id.VideoCallButton);
        sendNoteButton = findViewById(R.id.Sendnotes_btn);
        createTodoList = findViewById(R.id.todiList_btn);
        imgBtnMedicalRecord = findViewById(R.id.btnMedicalRecord);

        AlphaAnimation buttonClick = new AlphaAnimation(0.5f, 1F);
        buttonClick.setDuration(300);

        cancelButton = findViewById(R.id.CancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(buttonClick);
                //video call here
                if(patient !=null)
                    presenter.cancelAppointment(patient.getUsername(),Date);
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
                if (patient != null) {
                    //therapistPatientDetailViewPresenter.sendNote(patient.getUsername());
                    String username = patient.getUsername();
                    Intent intent=new Intent(TherapistPatientDetailvView.this,
                            CreateANoteActivity.class);
                    intent.putExtra("patient_username", username);
                    intent.putExtra("save",true);
                    startActivity(intent);
                }

                else
                    Toast.makeText(TherapistPatientDetailvView.this,"Patient null, please try again",Toast.LENGTH_SHORT).show();
            }
        });

        createTodoList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(buttonClick);
                if (patient != null) {
                    //therapistPatientDetailViewPresenter.createTodoList(patient.getUsername());
                    String username = patient.getUsername();
                    Intent intent=new Intent(TherapistPatientDetailvView.this,
                            CreateATodolistActivity.class);
                    intent.putExtra("patient_username", username);
                    intent.putExtra("save",true);
                    startActivity(intent);
                }
                else
                    Toast.makeText(TherapistPatientDetailvView.this,"Patient null, please try again",Toast.LENGTH_SHORT).show();
            }
        });

        imgBtnMedicalRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(buttonClick);
                String username = patient.getUsername();
                Intent intent=new Intent(
                        TherapistPatientDetailvView.this,
                        CreateMedicalRecordActivity.class
                );
                intent.putExtra("patient_username", username);
                startActivity(intent);
            }
        });
    }
/*
    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
*/
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        Intent intent = new Intent(this,TherapistViewAppointmentActivity.class);
//        startActivity(intent);
//        Log.d("AAA"," backpress of appointment");
//        finish();
//    }

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

    @Override
    public void cancelSuccessful() {
        Toast.makeText(this,"Cancel appoinment succesful",Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void cancelFail() {
        Toast.makeText(this,"Cancel appoinment fail, please try again",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setDialog(String username,String date) {
        DialogInterface.OnClickListener dialog = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case DialogInterface.BUTTON_POSITIVE:
                        presenter.removeAppointment(username,date);
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Confirm Cancellation");
        alertDialog.setMessage("Are you sure to cancel the appointment?");
        alertDialog.setPositiveButton("Yes",dialog).setNegativeButton("No",dialog).show();

    }
}