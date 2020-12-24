package com.example.onlinetherapist.appointment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlinetherapist.Constant;
import com.example.onlinetherapist.FirebaseManagement;
import com.example.onlinetherapist.Login.Patient;
import com.example.onlinetherapist.onReadDataListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import org.w3c.dom.Text;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;

public class ViewAppointmentPresenter implements IViewAppointmentPresenter.Presenter {
    IViewAppointmentView view;
    FirebaseManagement firebaseManagement;
    ViewAppointmentPresenter(IViewAppointmentView view){
        this.view = view;
        firebaseManagement = FirebaseManagement.getInstance();
    }

    @Override
    public void cancellation(final Activity activity) {
        final Date currentDate = Calendar.getInstance().getTime();
        SharedPreferences savedUsername = activity.getSharedPreferences("SavedUsername", MODE_PRIVATE);
        final String uname = savedUsername.getString("username", "");

        DialogInterface.OnClickListener dialog = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case DialogInterface.BUTTON_POSITIVE:
                        firebaseManagement.removeAppointment(currentDate, uname, new onReadDataListener() {
                            @Override
                            public void onStart() {

                            }

                            @Override
                            public void onSuccess(DataSnapshot data, String message) {
                                if(message.equals("Failed"))
                                    view.onFailCancel("You Can Only Cancel The Appointment Before It's Held 2 days");
                                else if(message.equals("Success Remove")) {
                                    view.onSuccessCancel("Your Appointment has been removed!");
                                    activity.finish();
                                    activity.startActivity(activity.getIntent());
                                }
                            }

                            @Override
                            public void onSuccessTimeSlot(ArrayList<TimeSlotModel> timeSlotModels) {

                            }

                            @Override
                            public void onSuccessAddNewAppointment() {

                            }

                            @Override
                            public void onSuccessGetPatientInfo(Patient patient) {

                            }

                            @Override
                            public void onFailed(DatabaseError e) {
                                view.onFailCancel("You Cannot Remove Appointment.");
                            }
                        });
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
        alertDialog.setTitle("Confirm Cancellation");
        alertDialog.setMessage("Are you sure to cancel the appointment?");
        alertDialog.setPositiveButton("Yes",dialog).setNegativeButton("No",dialog).show();
    }

    @Override
    public void displayPatientAppointment(Activity activity, TextView timeAppointment,
                                          TextView dateAppointment) {

    }

    @Override
    public void retrieveAppointment(final Activity activity, final TextView timeAppointment,
                                    final TextView dateAppointment) {
        final ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Please Wait..");
        SharedPreferences savedUsername = activity.getSharedPreferences("SavedUsername", MODE_PRIVATE);
        final String uname = savedUsername.getString("username", "");
        firebaseManagement.getBookAppointments(uname,
                new onReadDataListener() {
                    @Override
                    public void onStart() {
                        progressDialog.show();
                    }

                    @Override
                    public void onSuccess(DataSnapshot data, String message) {
                        if(data != null){
                            long status = (long) data.child("Status").getValue();
                            String date = data.child("Date").getValue().toString();
                            long slot = (long)data.child("Slot").getValue();

                            String time = "8:30 a.m";
                            if(slot == 2)
                                time = "2:30 p.m";
                            timeAppointment.setText(time);
                            dateAppointment.setText(date);
                        }
                        else{
                            String status = "None";
                            timeAppointment.setText(status);
                            dateAppointment.setText(status);
                        }
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onSuccessTimeSlot(ArrayList<TimeSlotModel> timeSlotModels) {

                    }

                    @Override
                    public void onSuccessAddNewAppointment() {

                    }

                    @Override
                    public void onSuccessGetPatientInfo(Patient patient) {

                    }

                    @Override
                    public void onFailed(DatabaseError e) {
                        progressDialog.dismiss();
                    }
                });
    }


}
