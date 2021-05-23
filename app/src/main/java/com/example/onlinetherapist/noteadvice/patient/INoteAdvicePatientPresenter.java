package com.example.onlinetherapist.noteadvice.patient;

public interface INoteAdvicePatientPresenter {
    void retrieveNotesData(String username);
    void retrieveTodolistData(String username);

    void updateNotesData ();
    void updateTodolistData ();
}
