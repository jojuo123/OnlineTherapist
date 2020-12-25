package com.example.onlinetherapist.noteadvice.patient;

import com.example.onlinetherapist.FirebaseManagement;
import com.example.onlinetherapist.noteadvice.TodolistItemModel;
import com.example.onlinetherapist.onReadDataListener;
import com.example.onlinetherapist.onSetValueListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.List;

public class ViewATodolistPresenter implements IViewATodolistPresenter {
    IViewATodolistView view;
    FirebaseManagement firebaseManagement;
    String username, listID;
    public ViewATodolistPresenter(IViewATodolistView view, String listID) {
        this.view = view;
        firebaseManagement = FirebaseManagement.getInstance();
        this.listID=listID;
    }

    @Override
    public void userInteractWithModel(TodolistItemModel model, boolean clicked) {
        firebaseManagement.setTodolistItemStatus(model, (clicked ? 1 : 0), new onSetValueListener() {
                    @Override
                    public void onSuccess(String message) {
                        retrieveTodolistItem();
                    }

                    @Override
                    public void onFailed(Exception e, String message) {
//                       //NO internet connection or sth
                    }
                });
    }

    @Override
    public void retrieveTodolistItem() {
        List<TodolistItemModel> modelList = new ArrayList<>();
        firebaseManagement.getTodolistItems(listID, new onReadDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(DataSnapshot data, String message) {
                if (data!=null) {
                    String id = data.child("ID").getValue().toString();
                    String list_id = data.child("List_ID").getValue().toString();
                    String content = data.child("Content").getValue().toString();
                    long status = (long) data.child("Status").getValue();

                    TodolistItemModel model = new TodolistItemModel(id,list_id,content, (int) status);
                    modelList.add(model);
                }
                else if (message.equals("Done")){
                    view.updateTodolistItems(modelList);
                }
            }

            @Override
            public void onFailed(DatabaseError e) {

            }
        });
    }
}
