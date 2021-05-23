package com.example.onlinetherapist.MedicalRecord.therapist;

import com.example.onlinetherapist.MedicalRecord.MedicalRecordModel;
import com.example.onlinetherapist.noteadvice.NoteModel;
import com.example.onlinetherapist.noteadvice.TodolistItemModel;
import com.example.onlinetherapist.noteadvice.TodolistModel;

import java.util.List;

public interface ICreateMedicalRecordContract {
    interface View{
        void pushTodoListComplete();
        void pushNoteComplete(String noteID);
        void finish();
    }

    interface Presenter{
        void pushMedicalRecordToServer(
                String therapistID,
                String patientID,
                String created_date,
                String problems,
                String diagnosis,
                String treatment,
                String noteID,
                String todoListID
        );

        void pushTodoListToServer(TodolistModel todolist, List<TodolistItemModel> todolistItems);
        void pushNotesToServer(String created_date, String content, String user_id);
    }
}
