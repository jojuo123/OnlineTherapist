package com.example.onlinetherapist.appointment;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


import com.example.onlinetherapist.R;

public class BookAppointmentActivity extends AppCompatActivity implements IBookAppointmentView {
    TimeRowModel t[] = {new TimeRowModel("21/12/2020",true,false),new TimeRowModel("22/12/2020",true,false),new TimeRowModel("23/12/202 ",true,true)};
    BookAppointmentPresenter bookAppointmentPresenter =new BookAppointmentPresenter(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);
        bookAppointmentPresenter.initTimeTable(t);

    }

    @Override
    public void initAvailableTimeTable() {
        TableLayout timeSlot = (TableLayout)findViewById(R.id.TimeTable);
        timeSlot.setStretchAllColumns(true);
        timeSlot.bringToFront();
        for(int i = 0; i < t.length; i++){
            TableRow tr =  new TableRow(this);
            TextView c1 = new TextView(this);
            c1.setBackgroundColor(Color.BLUE);
            c1.setText(String.valueOf(t[i].getDate()));
            Button c2 = new Button(this);
            c2.setText(String.valueOf(t[i].isMorningAvailable()));
            Button c3 = new Button(this);
            c3.setText(String.valueOf(t[i].isEveningAvailable()));
            tr.addView(c1);
            tr.addView(c2);
            tr.addView(c3);
            timeSlot.addView(tr);
        }
    }

    @Override
    public void bookAppointment(String date, int slot) {

    }
}