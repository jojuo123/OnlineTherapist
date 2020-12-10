package com.example.onlinetherapist.noteadvice.patient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.onlinetherapist.R;

public class NoteAdvicePatientActivity extends AppCompatActivity implements INoteAdvicePatientView{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_advice_patient);
    }
}