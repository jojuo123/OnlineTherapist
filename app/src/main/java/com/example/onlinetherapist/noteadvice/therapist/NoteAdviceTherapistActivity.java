package com.example.onlinetherapist.noteadvice.therapist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.onlinetherapist.R;

public class NoteAdviceTherapistActivity extends AppCompatActivity implements INoteAdviceTherapistView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_advice_therapist);
    }
}