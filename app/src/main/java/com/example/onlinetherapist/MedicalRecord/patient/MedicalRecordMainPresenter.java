package com.example.onlinetherapist.MedicalRecord.patient;

import android.app.Activity;

import com.example.onlinetherapist.Constant;
import com.example.onlinetherapist.FirebaseManagement;
import com.example.onlinetherapist.Login.Patient;
import com.example.onlinetherapist.MedicalRecord.MedicalRecordModel;
import com.example.onlinetherapist.appointment.TimeSlotModel;
import com.example.onlinetherapist.onReadDataListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

public class MedicalRecordMainPresenter implements IMedicalRecordMainContract.Presenter{
    IMedicalRecordMainContract.View view;
    FirebaseManagement firebaseManagement;
    public MedicalRecordMainPresenter(IMedicalRecordMainContract.View view) {
        this.view = view;
        firebaseManagement = FirebaseManagement.getInstance();
    }

    @Override
    public void fetchAllMedicalRecords(String patientID) {
        ArrayList<MedicalRecordModel> medicalRecordModels = new ArrayList<>();

        firebaseManagement.getMedicalRecords(patientID,new onReadDataListener(){
            @Override
            public void onStart() {}
            @Override
            public void onSuccess(DataSnapshot data, String message) {
                if(data!=null){
                    String id = data.child(Constant.RECORD_ID).getValue().toString();
                    String therapistID = data.child(Constant.RECORD_THERAPIST_ID).getValue().toString();
                    String patientID = data.child(Constant.RECORD_PATIENT_ID).getValue().toString();
                    String date = data.child(Constant.RECORD_DATE).getValue().toString();
                    String problem = data.child(Constant.RECORD_PROBLEM).getValue().toString();
                    String diagnosis = data.child(Constant.RECORD_DIAGNOSIS).getValue().toString();
                    String treatment = data.child(Constant.RECORD_TREATMENT).getValue().toString();
                    String noteID = data.child(Constant.RECORD_NOTE_ID).getValue().toString();
                    String todoListID = data.child(Constant.RECORD_TODOLIST_ID).getValue().toString();

                    MedicalRecordModel medicalRecordModel = new MedicalRecordModel(
                            id,
                            patientID,
                            therapistID,
                            date,
                            problem,
                            diagnosis,
                            treatment,
                            noteID,
                            todoListID);
                    medicalRecordModels.add(medicalRecordModel);
                }
                else if (message.equals("Done")){
                    view.setAllMedicalRecords(medicalRecordModels);
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
        });

        view.setAllMedicalRecords(medicalRecordModels);
    }
}
