package com.example.onlinetherapist.noteadvice.therapist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.onlinetherapist.R;

public class CreateATodolistItemActivity extends AppCompatActivity {

    EditText edTodolistItemContent;
    TextView tvCurrentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_atodolistitem);

        edTodolistItemContent=findViewById(R.id.noteContent);
        tvCurrentDate=findViewById(R.id.noteDate);
        tvCurrentDate.setText(getIntent().getStringExtra("current_date"));
    }

    public void onClickCancelBtn(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void onClickDoneBtn(View view) {
        Intent intent = new Intent();
        intent.putExtra("content", edTodolistItemContent.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }
}