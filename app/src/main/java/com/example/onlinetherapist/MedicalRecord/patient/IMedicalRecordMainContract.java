package com.example.onlinetherapist.MedicalRecord.patient;

import com.example.onlinetherapist.MedicalRecord.MedicalRecordModel;

import java.util.ArrayList;

public interface IMedicalRecordMainContract {
    interface View{
        void setAllMedicalRecords(ArrayList<MedicalRecordModel> medicalRecordModelArrayList);
    }

    interface Presenter{
        void fetchAllMedicalRecords(String patientID);
    }
}
