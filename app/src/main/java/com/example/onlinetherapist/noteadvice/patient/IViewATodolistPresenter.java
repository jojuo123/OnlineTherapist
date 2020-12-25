package com.example.onlinetherapist.noteadvice.patient;

import com.example.onlinetherapist.noteadvice.TodolistItemModel;

public interface IViewATodolistPresenter {
    void userInteractWithModel(TodolistItemModel model, boolean clicked);
    void retrieveTodolistItem ();
}
