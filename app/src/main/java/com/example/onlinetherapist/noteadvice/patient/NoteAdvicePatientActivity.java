package com.example.onlinetherapist.noteadvice.patient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.onlinetherapist.R;
import com.example.onlinetherapist.noteadvice.*;

import java.util.ArrayList;
import java.util.List;

public class NoteAdvicePatientActivity extends AppCompatActivity implements INoteAdvicePatientView{

    INoteAdvicePatientPresenter presenter;
    ListView listViewNoteAdvice, listViewTodolist;
    NoteAdapter noteAdapter;
    TodolistAdapter todolistAdapter;

    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_advice_patient);
        username = getIntent().getStringExtra("username");
        presenter = new NoteAdvicePatientPresenter(this);
        noteAdapter = new NoteAdapter(this, R.layout.listitem_note, new ArrayList<>());
        todolistAdapter = new TodolistAdapter(this, R.layout.listitem_todolist, new ArrayList<>());
        listViewNoteAdvice = findViewById(R.id.listViewNotes);
        listViewNoteAdvice.setAdapter(noteAdapter);
        listViewTodolist = findViewById(R.id.listViewTodolists);
        listViewTodolist.setAdapter(todolistAdapter);

        presenter.retrieveNotesData(username); //call updateNoteList once sucess
        presenter.retrieveTodolistData(username); //call updatetodolistList on sucess.

        listViewNoteAdvice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                NoteModel noteModel = noteAdapter.getItem(i);
                Intent intent = new Intent(NoteAdvicePatientActivity.this, ViewANoteActivity.class);
                intent.putExtra("date",noteModel.getDateString());
                intent.putExtra("content",noteModel.getContent());
                startActivity(intent);
            }
        });
        listViewTodolist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TodolistModel model = todolistAdapter.getItem(i);
                List<TodolistItemModel> itemModels = model.getTodolist();
                Intent intent = new Intent(NoteAdvicePatientActivity.this, ViewATodolistActivity.class);
                intent.putExtra("date",model.getCreated_dateString());
                intent.putExtra("id", model.getId());
                intent.putExtra("user_id", model.getUser_id());
                /**
                ///TRICK
                intent.putExtra("n", itemModels.size());
                for (int j=0; j<itemModels.size(); ++j) {
                    TodolistItemModel itemModel = itemModels.get(j);
                    String key = "item" + Integer.toString(j);
                    intent.putExtra(key+"_content", itemModel.getContent());
                    intent.putExtra(key+"_status", itemModel.getStatus());
                    intent.putExtra(key+"_id", itemModel.getId());
                    intent.putExtra(key+"_listid", itemModel.getList_id());
                }
                 **/
                startActivity(intent);
            }
        });
    }

    public void updateNoteList (List<NoteModel> newNoteList) {
        noteAdapter.updateDataset(newNoteList);
    }

    public void updateTodolistList (List<TodolistModel> newTodolistList) {
        todolistAdapter.updateDataset(newTodolistList);
    }

}