package com.example.onlinetherapist.appointment;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


import com.example.onlinetherapist.FirebaseManagement;
import com.example.onlinetherapist.R;
import java.util.ArrayList;

public class BookAppointmentActivity extends AppCompatActivity implements IBookAppointmentView {


    TextView textView;
    Button button;
    BookAppointmentPresenter bookAppointmentPresenter =new BookAppointmentPresenter(this,BookAppointmentActivity.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);
        textView = findViewById(R.id.DateTextView);
        button = findViewById(R.id.BookButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = (String) textView.getText();
                if ( !temp.equals("Date:"))
                    bookAppointmentPresenter.bookAppointmentAt(BookAppointmentActivity.this, (String) textView.getText());
                else Toast.makeText(BookAppointmentActivity.this,"Wrong time, please choose again",Toast.LENGTH_LONG).show();
            }
        });
        bookAppointmentPresenter.queryTimeSlotData();



    }

    @Override
    public void initAvailableTimeTable() {




    }

    @Override
    public void bookAppointment(String date, int slot)
    {

    }


}