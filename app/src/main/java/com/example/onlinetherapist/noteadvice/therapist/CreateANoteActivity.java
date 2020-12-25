package com.example.onlinetherapist.noteadvice.therapist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.onlinetherapist.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CreateANoteActivity extends AppCompatActivity implements ICreateANoteView {

    String patient_username;
    String current_date;

    EditText edContent;
    TextView tvNoteDate;

    ICreateANotePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_anote);

        initData();
        initVariables();
    }

    private void initVariables() {
        edContent=findViewById(R.id.noteContent);
        tvNoteDate=findViewById(R.id.noteDate); tvNoteDate.setText(current_date);
        presenter=new CreateANotePresenter(this, patient_username);
    }

    private void initData() {
        patient_username=getIntent().getStringExtra("patient_username");
        current_date=new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());

    }

    public void onClickCancelBtn(View view) {
        finish();
    }


    public void onClickDoneBtn(View view) {
        presenter.createANote(current_date, edContent.getText().toString(), patient_username);
    }
}