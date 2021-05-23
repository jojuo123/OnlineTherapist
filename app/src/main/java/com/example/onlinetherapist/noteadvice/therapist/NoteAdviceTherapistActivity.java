package com.example.onlinetherapist.noteadvice.therapist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.onlinetherapist.R;
import com.example.onlinetherapist.noteadvice.therapist.note.CreateANoteActivity;
import com.example.onlinetherapist.noteadvice.therapist.todoList.CreateATodolistActivity;

public class NoteAdviceTherapistActivity extends AppCompatActivity implements INoteAdviceTherapistView {

    String patient_username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_advice_therapist);

        //TODO Get the username from caller of note / todolist
        patient_username = "helloworld123"; //Default, man
        //patient_username=getIntent().getStringExtra("pusername");
    }

    public void onClickAddNote(View view) {
        Intent intent = new Intent(this, CreateANoteActivity.class);
        intent.putExtra("patient_username", patient_username);
        startActivity(intent);
    }

    public void onClickAddTodoList(View view) {
        Intent intent = new Intent(this, CreateATodolistActivity.class);
        intent.putExtra("patient_username", patient_username);
        startActivity(intent);
    }

    public void onClickCancelBtn(View view) {
        finish();
    }
}