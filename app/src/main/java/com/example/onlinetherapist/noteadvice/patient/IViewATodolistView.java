package com.example.onlinetherapist.noteadvice.patient;

import com.example.onlinetherapist.noteadvice.TodolistItemModel;

import java.util.List;

public interface IViewATodolistView {
    void updateTodolistItems (List<TodolistItemModel> newDataset);
}
