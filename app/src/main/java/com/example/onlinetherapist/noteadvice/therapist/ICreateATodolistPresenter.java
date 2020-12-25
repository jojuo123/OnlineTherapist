package com.example.onlinetherapist.noteadvice.therapist;

import com.example.onlinetherapist.noteadvice.TodolistItemModel;
import com.example.onlinetherapist.noteadvice.TodolistModel;
import com.example.onlinetherapist.onSetValueListener;

import java.util.List;

public interface ICreateATodolistPresenter {
    void uploadNewTodolist (TodolistModel todolistModel, List<TodolistItemModel> todolistItemModels);
}
