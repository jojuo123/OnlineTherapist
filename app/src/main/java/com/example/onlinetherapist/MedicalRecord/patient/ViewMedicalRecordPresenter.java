package com.example.onlinetherapist.MedicalRecord.patient;

import android.app.Activity;
import android.content.Intent;

import com.example.onlinetherapist.FirebaseManagement;
import com.example.onlinetherapist.Login.Patient;
import com.example.onlinetherapist.MedicalRecord.MedicalRecordModel;
import com.example.onlinetherapist.appointment.TimeSlotModel;
import com.example.onlinetherapist.noteadvice.NoteModel;
import com.example.onlinetherapist.onReadDataListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

public class ViewMedicalRecordPresenter implements IViewMedicalRecordContract.Presenter {
    IViewMedicalRecordContract.View view;
    FirebaseManagement firebaseManagement;

    public ViewMedicalRecordPresenter(IViewMedicalRecordContract.View view) {
        this.view = view;
        firebaseManagement = FirebaseManagement.getInstance();
    }

    @Override
    public void getMedicalRecordDetail() {
        view.getIntentData();
        view.setMedicalRecordDetail();
    }

    @Override
    public void getRecordNoteDetail(String ID) {
        firebaseManagement.getNotes(ID, new onReadDataListener() {
            @Override
            public void onStart() {}

            @Override
            public void onSuccess(DataSnapshot data, String message) {
                if(data!=null){
                    String id = data.child("ID").getValue().toString();
                    String user_id = data.child("User_ID").getValue().toString();
                    String content = data.child("Content").getValue().toString();
                    String date = data.child("Date").getValue().toString(); //? What for?
                    long status = (long) data.child("Status").getValue();

                    NoteModel noteModel = new NoteModel(id,user_id,content, (int) status,date);
                    view.assignNote(noteModel);
                }
            }

            @Override
            public void onSuccessTimeSlot(ArrayList<TimeSlotModel> timeSlotModels) {}

            @Override
            public void onSuccessAddNewAppointment() {}

            @Override
            public void onSuccessGetPatientInfo(Patient patient) {}

            @Override
            public void onFailed(DatabaseError e) {}
        },"ID");
    }

}
