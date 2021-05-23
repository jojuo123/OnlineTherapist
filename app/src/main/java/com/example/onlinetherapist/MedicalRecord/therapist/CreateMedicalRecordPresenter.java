package com.example.onlinetherapist.MedicalRecord.therapist;

import com.example.onlinetherapist.FirebaseManagement;
import com.example.onlinetherapist.noteadvice.NoteModel;
import com.example.onlinetherapist.noteadvice.TodolistItemModel;
import com.example.onlinetherapist.noteadvice.TodolistModel;
import com.example.onlinetherapist.onSetValueListener;

import java.util.List;

public class CreateMedicalRecordPresenter implements ICreateMedicalRecordContract.Presenter{
    ICreateMedicalRecordContract.View view;
    FirebaseManagement firebaseManagement;

    public CreateMedicalRecordPresenter(ICreateMedicalRecordContract.View view) {
        this.view = view;
        firebaseManagement = FirebaseManagement.getInstance();
    }

    @Override
    public void pushMedicalRecordToServer(
            String therapistID,
            String patientID,
            String created_date,
            String problems,
            String diagnosis,
            String treatment,
            String noteID,
            String todoListID
    ){
        firebaseManagement.pushNewMedicalRecord(
                therapistID,
                patientID,
                created_date,
                problems,
                diagnosis,
                treatment,
                noteID,
                todoListID,
                new onSetValueListener() {
                    @Override
                    public void onSuccess(String message) {
                        view.finish();
                    }

                    @Override
                    public void onFailed(Exception e, String message) {

                    }
                });
    }

    @Override
    public void pushTodoListToServer(TodolistModel todolistModel, List<TodolistItemModel> todolistItemModels) {
        firebaseManagement.newTodolist(todolistModel, todolistItemModels, new onSetValueListener() {
            @Override
            public void onSuccess(String message) {
                if (message == "todolist_sucess") {
                    view.pushTodoListComplete();
                }
            }
            @Override
            public void onFailed(Exception e, String message) {

            }
        });
    }

    @Override
    public void pushNotesToServer(String created_date, String content, String user_id) {
        firebaseManagement.newNote(created_date, content, user_id, new onSetValueListener() {
            @Override
            public void onSuccess(String message) {
                String noteID = message;
                view.pushNoteComplete(noteID);
            }

            @Override
            public void onFailed(Exception e, String message) {
                //WARN NO INTERNET CONNECTION or sth
            }
        });
    }
}
