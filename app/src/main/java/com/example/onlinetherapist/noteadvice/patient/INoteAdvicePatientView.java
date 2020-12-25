package com.example.onlinetherapist.noteadvice.patient;

import com.example.onlinetherapist.noteadvice.NoteModel;
import com.example.onlinetherapist.noteadvice.TodolistModel;

import java.util.List;

public interface INoteAdvicePatientView {
    void updateNoteList (List<NoteModel> newNoteList);
    void updateTodolistList (List<TodolistModel> newTodolistList);
}
