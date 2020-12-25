package com.example.onlinetherapist.noteadvice.therapist;

import com.example.onlinetherapist.FirebaseManagement;
import com.example.onlinetherapist.noteadvice.NoteModel;
import com.example.onlinetherapist.onSetValueListener;

public class CreateANotePresenter implements ICreateANotePresenter {
    ICreateANoteView view;
    FirebaseManagement firebaseManagement;
    String patient_username;
    public CreateANotePresenter(ICreateANoteView view, String patient_username) {
        this.view=view;
        this.patient_username=patient_username;
        firebaseManagement=FirebaseManagement.getInstance();
    }


    @Override
    public void createANote(String created_date, String content, String user_id) {
        firebaseManagement.newNote(created_date, content, user_id, new onSetValueListener() {
            @Override
            public void onSuccess(String message) {
                view.finish();
            }

            @Override
            public void onFailed(Exception e, String message) {
                //WARN NO INTERNET CONNECTION or sth
            }
        });
    }
}
