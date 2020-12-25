package com.example.onlinetherapist.noteadvice.patient;

import com.example.onlinetherapist.FirebaseManagement;
import com.example.onlinetherapist.noteadvice.NoteModel;
import com.example.onlinetherapist.noteadvice.TodolistModel;
import com.example.onlinetherapist.onReadDataListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NoteAdvicePatientPresenter implements INoteAdvicePatientPresenter{
    INoteAdvicePatientView view;
    FirebaseManagement firebaseManagement;
    NoteAdvicePatientPresenter (INoteAdvicePatientView view) {
        this.view = view;
        firebaseManagement = FirebaseManagement.getInstance();
    }


    @Override
    public void updateNotesData() {

    }

    @Override
    public void retrieveNotesData(String username) {
        List<NoteModel> datalist = new ArrayList<>();
        firebaseManagement.getNotes(username, new onReadDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(DataSnapshot data, String message) {
                if (data!=null) {
                    String id = data.child("ID").getValue().toString();
                    String user_id = data.child("User_ID").getValue().toString();
                    String content = data.child("Content").getValue().toString();
                    String date = data.child("Date").getValue().toString(); //? What for?
                    long status = (long) data.child("Status").getValue();

                    NoteModel noteModel = new NoteModel(id,user_id,content, (int) status,date);
                    datalist.add(noteModel);
                }
                else if (message.equals("Done")){
                    view.updateNoteList(datalist);
                }
            }

            @Override
            public void onFailed(DatabaseError e) {

            }
        });
    }

    @Override
    public void retrieveTodolistData(String username) {
        List<TodolistModel> datalist = new ArrayList<>();
        firebaseManagement.getTodolists(username, new onReadDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(DataSnapshot data, String message) {
                if (data!=null) {
                    String id = data.child("ID").getValue().toString();
                    String user_id = data.child("User_ID").getValue().toString();
                    String date = data.child("Created_Date").getValue().toString(); //Date ???

                    TodolistModel model = new TodolistModel(id, user_id, date);
                    datalist.add(model);
                }
                else if (message.equals("Done")){
                    view.updateTodolistList(datalist);
                }
            }

            @Override
            public void onFailed(DatabaseError e) {

            }
        });
    }

    @Override
    public void updateTodolistData() {

    }
}
