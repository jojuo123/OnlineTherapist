package com.example.onlinetherapist.appointment.therapist;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.example.onlinetherapist.FirebaseManagement;
import com.example.onlinetherapist.Login.Patient;
import com.example.onlinetherapist.R;
import com.example.onlinetherapist.appointment.IBookAppointmentView;
import com.example.onlinetherapist.appointment.TimeRowModel;
import com.example.onlinetherapist.appointment.TimeSlotModel;
import com.example.onlinetherapist.homescreen.therapist.ITherapistHomePresenter;
import com.example.onlinetherapist.onReadDataListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.ZonedDateTime;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class TherapistViewAppointmentPresenter implements ITherapistHomePresenter {

    private FirebaseManagement firebaseManagement;
    private ArrayList<TimeRowModelTherapist> timeRowModelTherapists;
    private ArrayList<TimeSlotModel> timeSlotModelArrayList;
    private Activity activity;
    public TherapistViewAppointmentPresenter(Activity activity)
    {
        this.activity = activity   ;
        firebaseManagement = FirebaseManagement.getInstance();

    }

    public void queryTimeSlotData()
    {
        final ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Please Wait..");

        firebaseManagement.getTimeSlot(new onReadDataListener() {
            @Override
            public void onStart() {
                progressDialog.show();
            }

            @Override
            public void onSuccess(DataSnapshot data, String message) {

            }

            @Override
            public void onSuccessTimeSlot(ArrayList<TimeSlotModel> timeSlotModels) {
                timeRowModelTherapists =  new ArrayList<TimeRowModelTherapist>();

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    ZonedDateTime zonedDateTime =ZonedDateTime.now();
                    for(int i =0;i<=14;++i)
                    {
                        if(zonedDateTime.getDayOfWeek() != DayOfWeek.SATURDAY && zonedDateTime.getDayOfWeek() != DayOfWeek.SATURDAY)
                        {
                            String day = String.valueOf(zonedDateTime.getDayOfMonth());
                            String month = String.valueOf(zonedDateTime.getMonthValue());
                            if(day.length() ==1)
                                day = "0".concat(day);
                            if(month.length() ==1)
                                month = "0".concat(month);
                            TimeRowModelTherapist temp =  new TimeRowModelTherapist(day.concat("/").concat(month).concat("/").concat(String.valueOf(zonedDateTime.getYear())),"Available","Available");
                            timeRowModelTherapists.add(temp);
                        }

                        zonedDateTime = zonedDateTime.plusDays(1);
                    }

                }

                timeSlotModelArrayList = timeSlotModels;
                for (int i = 0; i < timeRowModelTherapists.size(); ++i) {
                    for (TimeSlotModel t : timeSlotModelArrayList) {
                        if (t.getDate().equals(timeRowModelTherapists.get(i).getDate())) {
                            if (t.getStatus() == 1) {
                                if (t.getSlot() == 1) {
                                    timeRowModelTherapists.get(i).setMorningBook(t.getUser_ID());
                                } else {
                                    timeRowModelTherapists.get(i).setEveningBook(t.getUser_ID());
                                }

                            }
                            else if(t.getStatus() ==2)
                            {
                                if (t.getSlot() == 1) {
                                    timeRowModelTherapists.get(i).setMorningBook("Therapist cancel");
                                } else {
                                    timeRowModelTherapists.get(i).setEveningBook("Therapist cancel");
                                }
                            }

                        }
                    }
                }
                TableLayout timeSlotTable = (TableLayout) activity.findViewById(R.id.TimeTableTherapist);
                timeSlotTable.removeAllViews();

                timeSlotTable.setStretchAllColumns(true);
                timeSlotTable.bringToFront();
                float textSize = 17;
                TextView t1 = new TextView(activity);
                t1.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL);
                t1.setText("DATE");
                t1.setTextSize(textSize);
                TextView t2 = new TextView(activity);
                t2.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL);
                t2.setText("MORNING");
                t2.setTextSize(textSize);
                TextView t3 = new TextView(activity);
                t3.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL);
                t3.setText("EVENING");
                t3.setTextSize(textSize);
                TableRow tableRow = new TableRow(activity);
                tableRow.addView(t1);
                tableRow.addView(t2);
                tableRow.addView(t3);
                timeSlotTable.addView(tableRow);




                AlphaAnimation buttonClick = new AlphaAnimation(0.5f, 1F);
                buttonClick.setDuration(300);
                for (int i = 0; i < timeRowModelTherapists.size(); i++) {
                    float size = 12;
                    TableRow tr = new TableRow(activity);
                    TextView c1 = new TextView(activity);
                    String date = String.valueOf(timeRowModelTherapists.get(i).getDate());
                    c1.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL);
                    c1.setText(date);
                    c1.setTextSize(17);
                    Button c2 = new Button(activity);


                    c2.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL);
                    c2.setTextSize(size);
                    //c2.setBackgroundColor(0);

                    if (timeRowModelTherapists.get(i).getMorningBook().equals("Therapist cancel"))
                    {
                        c2.setBackground(ContextCompat.getDrawable(activity,R.drawable.therapist_cancel_button));
                    }
                    else if (timeRowModelTherapists.get(i).getMorningBook().equals("Available"))
                    {
                        c2.setBackground(ContextCompat.getDrawable(activity,R.drawable.available_button));
                    }
                    else {
                            c2.setBackground(ContextCompat.getDrawable(activity, R.drawable.not_available_button));
                    }
                    c2.setText(timeRowModelTherapists.get(i).getMorningBook());
                    if(!c2.getText().equals("Available") && !c2.getText().equals("Therapist cancel") ) {
                        c2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                v.startAnimation(buttonClick);
                                firebaseManagement.getPatientInfo(new onReadDataListener() {
                                    @Override
                                    public void onStart() {

                                    }

                                    @Override
                                    public void onSuccess(DataSnapshot data, String message) {

                                    }

                                    @Override
                                    public void onSuccessTimeSlot(ArrayList<TimeSlotModel> timeSlotModels) {

                                    }

                                    @Override
                                    public void onSuccessAddNewAppointment() {

                                    }

                                    @Override
                                    public void onSuccessGetPatientInfo(Patient patient) {
                                        Intent intent = new Intent(activity, TherapistPatientDetailvView.class).putExtra("Patient", patient);
                                        activity.startActivity(intent);

                                    }

                                    @Override
                                    public void onFailed(DatabaseError e) {

                                    }
                                }, (String) c2.getText());

                            }
                        });
                    }
                    Button c3 = new Button(activity);

                    c3.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL);
                    c3.setTextSize(size);
                    if (timeRowModelTherapists.get(i).getEveningBook().equals("Therapist cancel") )
                    {
                        c3.setBackground(ContextCompat.getDrawable(activity,R.drawable.therapist_cancel_button));
                    }
                    else if (timeRowModelTherapists.get(i).getEveningBook().equals("Available"))
                    {
                        c3.setBackground(ContextCompat.getDrawable(activity,R.drawable.available_button));
                    }
                    else {
                        c3.setBackground(ContextCompat.getDrawable(activity, R.drawable.not_available_button));
                    }
                    c3.setText(timeRowModelTherapists.get(i).getEveningBook());
                    c3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            v.startAnimation(buttonClick);
                            if( !c3.getText().equals("Available") &&  !c3.getText().equals("Therapist cancel"))
                            {
                                firebaseManagement.getPatientInfo(new onReadDataListener() {
                                    @Override
                                    public void onStart() {

                                    }

                                    @Override
                                    public void onSuccess(DataSnapshot data, String message) {

                                    }

                                    @Override
                                    public void onSuccessTimeSlot(ArrayList<TimeSlotModel> timeSlotModels) {

                                    }

                                    @Override
                                    public void onSuccessAddNewAppointment() {

                                    }

                                    @Override
                                    public void onSuccessGetPatientInfo(Patient patient) {
                                        Intent intent = new Intent(activity, TherapistPatientDetailvView.class).putExtra("Patient", patient);
                                        activity.startActivity(intent);

                                    }

                                    @Override
                                    public void onFailed(DatabaseError e) {

                                    }
                                }, (String) c3.getText());
                            }
                        }
                    });
                    tr.addView(c1);
                    tr.addView(c2);
                    tr.addView(c3);
                    timeSlotTable.addView(tr);
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onSuccessAddNewAppointment() {

            }

            @Override
            public void onSuccessGetPatientInfo(Patient patient) {

            }

            @Override
            public void onFailed(DatabaseError e) {
                //progressDialog.dismiss();
            }
        });
    }





}
