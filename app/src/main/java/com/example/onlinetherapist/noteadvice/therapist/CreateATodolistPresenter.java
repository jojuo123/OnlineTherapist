package com.example.onlinetherapist.noteadvice.therapist;

import com.example.onlinetherapist.FirebaseManagement;
import com.example.onlinetherapist.noteadvice.TodolistItemModel;
import com.example.onlinetherapist.noteadvice.TodolistModel;
import com.example.onlinetherapist.onSetValueListener;

import java.util.List;

public class CreateATodolistPresenter implements ICreateATodolistPresenter {
    ICreateATodolistView view;
    String patient_username;
    FirebaseManagement firebaseManagement;

    public CreateATodolistPresenter(ICreateATodolistView view, String patient_username) {
        this.view=view;
        this.patient_username=patient_username;
        firebaseManagement= FirebaseManagement.getInstance();
    }


    @Override
    public void uploadNewTodolist(TodolistModel todolistModel, List<TodolistItemModel> todolistItemModels) {
        firebaseManagement.newTodolist(todolistModel, todolistItemModels, new onSetValueListener() {
            @Override
            public void onSuccess(String message) {
                if (message == "todolist_sucess") {
                    view.finish();
                }
            }
            @Override
            public void onFailed(Exception e, String message) {

            }
        });
    }
}
