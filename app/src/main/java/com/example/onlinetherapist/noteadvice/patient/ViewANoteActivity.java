package com.example.onlinetherapist.noteadvice.patient;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.onlinetherapist.R;

public class ViewANoteActivity extends AppCompatActivity {

    TextView textViewDate, textViewContent;
    String noteDate, noteContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_anote);
        textViewContent = findViewById(R.id.noteContent);
        textViewDate = findViewById(R.id.noteDate);

        noteDate = getIntent().getStringExtra("date");
        noteContent = getIntent().getStringExtra("content");
        textViewDate.setText(noteDate);
        textViewContent.setText(noteContent);
    }
}