package com.example.onlinetherapist.appointment.therapist;

import com.example.onlinetherapist.FirebaseManagement;
import com.example.onlinetherapist.Login.Patient;
import com.example.onlinetherapist.appointment.TimeSlotModel;
import com.example.onlinetherapist.onReadDataListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

public class TherapistPatientDetailViewPresenter implements ITherapistPatientDetailViewContract.Presenter{
    FirebaseManagement firebaseManagement;
    ITherapistPatientDetailViewContract.View view;

    public TherapistPatientDetailViewPresenter(ITherapistPatientDetailViewContract.View view) {
        this.firebaseManagement = FirebaseManagement.getInstance();
        this.view = view;
    }


    @Override
    public void cancelAppointment(String username,String date)
    {
        view.setDialog(username,date);
    }

    @Override
    public void removeAppointment(String username,String date) {
        firebaseManagement.cancelAppointment(username, new onReadDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(DataSnapshot data, String message) {
//                                TextView dob = activity.findViewById(R.id.dob);
//                                TextView username =activity.findViewById(R.id.username);
//                                TextView height = activity.findViewById(R.id.user_height);
//                                TextView weight =activity.findViewById(R.id.user_weight);
//                                TextView sex =activity.findViewById(R.id.sex);

                view.cancelSuccessful();

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
                view.cancelFail();
//                                Toast.makeText(activity,"Cancel appoinment fail, please try again",Toast.LENGTH_SHORT).show();
            }
        },date);
    }


}
