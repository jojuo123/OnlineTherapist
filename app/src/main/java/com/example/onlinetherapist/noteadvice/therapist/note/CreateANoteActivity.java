package com.example.onlinetherapist.noteadvice.therapist.note;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.onlinetherapist.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CreateANoteActivity extends AppCompatActivity implements ICreateANoteView {

    String patient_username;
    String current_date;
    boolean save=true;

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
        Intent intent = getIntent();
        patient_username=intent.getStringExtra("patient_username");
        save = intent.getBooleanExtra("save",true);
        current_date=new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
    }

    public void onClickCancelBtn(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }


    public void onClickDoneBtn(View view) {
        presenter.createANote(current_date, edContent.getText().toString(), patient_username,save);
    }

    @Override
    public void setResultFinish() {
        Intent intent = new Intent();
        intent.putExtra("content",edContent.getText().toString());
        setResult(RESULT_OK,intent);
        finish();
    }
}