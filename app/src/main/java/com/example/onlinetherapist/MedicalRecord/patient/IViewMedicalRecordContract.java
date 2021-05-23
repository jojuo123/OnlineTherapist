package com.example.onlinetherapist.MedicalRecord.patient;

import com.example.onlinetherapist.MedicalRecord.MedicalRecordModel;
import com.example.onlinetherapist.noteadvice.NoteModel;

public interface IViewMedicalRecordContract {
    interface View{
        void setMedicalRecordDetail();
        void getIntentData();
        void assignNote(NoteModel noteModel);
    }

    interface Presenter{
        void getMedicalRecordDetail();
        void getRecordNoteDetail(String ID);
    }
}
