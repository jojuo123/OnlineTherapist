package com.example.onlinetherapist.noteadvice.therapist.note;

public interface ICreateANotePresenter {
    void createANote(String created_date, String content, String user_id, boolean save);

}
