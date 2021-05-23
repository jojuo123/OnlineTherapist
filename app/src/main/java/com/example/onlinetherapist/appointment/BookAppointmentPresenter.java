package com.example.onlinetherapist.appointment;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.example.onlinetherapist.Login.Patient;
import com.example.onlinetherapist.R;
import com.example.onlinetherapist.appointment.TimeRowModel;
import com.example.onlinetherapist.FirebaseManagement;
import java.time.DayOfWeek;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import com.example.onlinetherapist.appointment.IBookAppointmentView;
import com.example.onlinetherapist.onReadDataListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import static android.content.Context.MODE_PRIVATE;


public class BookAppointmentPresenter implements IBookAppointmentPresenter{
    private IBookAppointmentView iBookAppointmentView;
    private FirebaseManagement firebaseManagement;
    private ArrayList<TimeRowModel> timeRowModels;
    private ArrayList<TimeSlotModel> timeSlotModelArrayList;
    private Activity activity;
    public BookAppointmentPresenter(IBookAppointmentView iBookAppointmentView,Activity activity)
    {
        this.activity = activity   ;
        this.iBookAppointmentView = iBookAppointmentView;
        firebaseManagement = FirebaseManagement.getInstance();
    }

    public void bookAppointmentAt(final Activity activity,String time)
    {
        firebaseManagement.getTimeSlot(new onReadDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(DataSnapshot data, String message) {

            }

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onSuccessTimeSlot(ArrayList<TimeSlotModel> timeSlotModels) {
                SharedPreferences savedUsername = activity.getSharedPreferences("SavedUsername", MODE_PRIVATE);
                final String uname = savedUsername.getString("username", "");
                ZonedDateTime zonedDateTime = ZonedDateTime.now();
                int cur_day = zonedDateTime.getDayOfMonth();
                int cur_month  = zonedDateTime.getMonthValue();
                int cur_year = zonedDateTime.getYear();


                for (TimeSlotModel t:timeSlotModels)
                {

                    if (t.getUser_ID().equals(uname)  && t.getStatus() ==1 )
                    {
                        String temp = t.getDate();

                        int Date = Integer.valueOf(temp.split("/")[0].toString());
                        int Month = Integer.valueOf(temp.split("/")[1].toString());
                        int Year = Integer.valueOf(temp.split("/")[2].toString());
                        if( cur_year <= Year && cur_month <= Month && cur_day <Date) {
                            Toast.makeText(activity, " Sorry, you can only book 1 slot at a time, please cancel the current slot to continue", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }

                String temp = time;
                String [] separated = temp.split(" ");
                String Date = separated[1];
                long Slot=0;
                long Status=0;
                if (separated[2].equals("Morning"))
                {
                    Slot = 1;
                    Status = 1;
                }
                else if(separated[2].equals("Evening"))
                {
                    Slot =2;
                    Status = 1;
                }

                TimeSlotModel timeSlotModel =  new TimeSlotModel(Date,Slot,Status,uname);
                FirebaseManagement.getInstance().addNewAppointment(timeSlotModel, new onReadDataListener() {
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
                        queryTimeSlotData();
                    }

                    @Override
                    public void onSuccessGetPatientInfo(Patient patient) {

                    }

                    @Override
                    public void onFailed(DatabaseError e) {

                    }
                });

            }

            @Override
            public void onSuccessAddNewAppointment() {

            }

            @Override
            public void onSuccessGetPatientInfo(Patient patient) {

            }

            @Override
            public void onFailed(DatabaseError e) {

            }
        });

    }

    public void queryTimeSlotData()
    {
        final ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Please Wait..");
        SharedPreferences savedUsername = activity.getSharedPreferences("SavedUsername", MODE_PRIVATE);
        final String uname = savedUsername.getString("username", "");
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
                timeRowModels =  new ArrayList<TimeRowModel>();

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
                {
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
                            TimeRowModel temp =  new TimeRowModel(day.concat("/").concat(month).concat("/").concat(String.valueOf(zonedDateTime.getYear())),0,0);
                            timeRowModels.add(temp);
                        }

                        zonedDateTime = zonedDateTime.plusDays(1);
                    }

                }
                int temp =-1;
                int isMorning =0;
                timeSlotModelArrayList = timeSlotModels;
                for (int i = 0; i < timeRowModels.size(); ++i) {
                    for (TimeSlotModel t : timeSlotModelArrayList) {
                        if (t.getDate().equals(timeRowModels.get(i).getDate())) {
                            if (t.getStatus() == 1 || t.getStatus() ==2) {
                                if (t.getSlot() == 1) {
                                    timeRowModels.get(i).setMorningAvailable(1);
                                } else {
                                    timeRowModels.get(i).setEveningAvailable(1);
                                }
                            }
                            if (t.getUser_ID().equals(uname) && t.getStatus() ==1)
                            {
                                temp = i;
                                if (t.getSlot() == 1) {
                                    isMorning = 1;
                                } else {
                                    isMorning=0;
                                }
                            }
                        }
                    }
                }
                TableLayout timeSlotTable = (TableLayout) activity.findViewById(R.id.TimeTable);
                timeSlotTable.removeAllViews();
                TextView textView = activity.findViewById(R.id.DateTextView);
                timeSlotTable.setStretchAllColumns(true);
                timeSlotTable.bringToFront();
                float textSize = 17;
                TextView t1 = new TextView(activity);
                t1.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL);
                t1.setTextColor(Color.WHITE);
                t1.setText("DATE");
                t1.setTextSize(textSize);
                TextView t2 = new TextView(activity);
                t2.setTextColor(Color.WHITE);
                t2.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL);
                t2.setText("MORNING");
                t2.setTextSize(textSize);
                TextView t3 = new TextView(activity);
                t3.setTextColor(Color.WHITE);
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
                for (int i = 0; i < timeRowModels.size(); i++) {
                    float size = 12;
                    TableRow tr = new TableRow(activity);
                    TextView c1 = new TextView(activity);
                    String date = String.valueOf(timeRowModels.get(i).getDate());
                    c1.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL);
                    c1.setTextColor(Color.WHITE);
                    c1.setText(date);
                    c1.setTextSize(17);
                    Button c2 = new Button(activity);


                    c2.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL);
                    c2.setTextSize(size);

                    //c2.setBackgroundColor(0);

                    if (timeRowModels.get(i).isMorningAvailable() == 0)
                    {
                        c2.setText("Available");
                        c2.setBackground(ContextCompat.getDrawable(activity,R.drawable.available_button));

                    }
                    else {
                        if( temp == i && isMorning == 1) {
                            c2.setBackground(ContextCompat.getDrawable(activity, R.drawable.your_booked_button));
                            c2.setTextSize(11);
                            c2.setText("Your booking");
                        }
                        else {
                            c2.setBackground(ContextCompat.getDrawable(activity, R.drawable.not_available_button));
                            c2.setText("Not available");
                        }

                    }
                    c2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            v.startAnimation(buttonClick);
                            if(!c2.getText().equals("Not available"))
                                textView.setText("Date: "+date+" Morning");
                            else Toast.makeText(activity,"Not available time, please choose again",Toast.LENGTH_SHORT).show();
                        }
                    });
                    Button c3 = new Button(activity);

                    c3.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL);
                    c3.setTextSize(size);

                    if (timeRowModels.get(i).isEveningAvailable() == 0) {
                        c3.setBackground(ContextCompat.getDrawable(activity,R.drawable.available_button));
                        c3.setText("Available");

                    }
                    else {
                        if (temp == i && isMorning == 0)
                        {
                            c3.setBackground(ContextCompat.getDrawable(activity, R.drawable.your_booked_button));
                            c3.setTextSize(11);
                            c3.setText("Your booking");
                        }
                        else {
                            c3.setBackground(ContextCompat.getDrawable(activity, R.drawable.not_available_button));
                            c3.setText("Not available");
                        }

                    }
                    c3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            v.startAnimation(buttonClick);
                            if(!c3.getText().equals("Not available"))
                                textView.setText("Date: "+date+" Evening");
                            else Toast.makeText(activity,"Not available time, please choose again",Toast.LENGTH_SHORT).show();
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
