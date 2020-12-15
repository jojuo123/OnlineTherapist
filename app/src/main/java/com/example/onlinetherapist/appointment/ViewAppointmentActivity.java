package com.example.onlinetherapist.appointment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlinetherapist.R;

public class ViewAppointmentActivity extends AppCompatActivity implements IViewAppointmentView {
    TextView timeAppointment, dateAppointment, statusAppointment;
    ImageButton cancelAppointment;
    ViewAppointmentPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_appointment);
        presenter = new ViewAppointmentPresenter((IViewAppointmentView) this);
        displayAppointment();
        onCancelClicked();

    }

    private void refresh() {
        finish();
        startActivity(getIntent());
    }

    public void displayAppointment(){
        timeAppointment = findViewById(R.id.time_appointment);
        dateAppointment = findViewById(R.id.date_appointment);
        statusAppointment = findViewById(R.id.status_appointment);

        presenter.retrieveAppointment(this, timeAppointment, dateAppointment);

    }
    public void onCancelClicked(){

        cancelAppointment = findViewById(R.id.cancel_appointment);
        cancelAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.cancellation(ViewAppointmentActivity.this);

            }
        });
    }
    @Override
    public void onFailCancel(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccessCancel(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}