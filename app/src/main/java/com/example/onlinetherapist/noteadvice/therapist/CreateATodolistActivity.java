package com.example.onlinetherapist.noteadvice.therapist;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.onlinetherapist.R;
import com.example.onlinetherapist.noteadvice.TodolistItemAdapter;
import com.example.onlinetherapist.noteadvice.TodolistItemModel;
import com.example.onlinetherapist.noteadvice.TodolistModel;
import com.google.android.gms.common.api.ResultTransform;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CreateATodolistActivity extends AppCompatActivity implements ICreateATodolistView {

    String patient_username;
    String current_date;
    String current_listID;

    ListView lvTodolistItems;
    private TextView tvNoteDate;

    ICreateATodolistPresenter presenter;
    List<TodolistItemModel> currentTodolist;
    TodolistModel currentTodolistModel;
    TodolistItemAdapter todolistItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_atodolist);
        initData();
        initVariables();
    }

    private void initVariables() {
        presenter=new CreateATodolistPresenter(this, patient_username);
        todolistItemAdapter = new TodolistItemAdapter(this, R.layout.listitem_todolistitem, currentTodolist);
        lvTodolistItems=findViewById(R.id.listViewTodolist);
        lvTodolistItems.setAdapter(todolistItemAdapter);
        tvNoteDate=findViewById(R.id.noteDate); tvNoteDate.setText(current_date);
    }

    private void initData() {
        patient_username=getIntent().getStringExtra("patient_username");
        current_date=new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
        currentTodolist = new ArrayList<>();
        current_listID = Long.toString(System.currentTimeMillis())+"_"+patient_username;
        currentTodolistModel = new TodolistModel(current_listID, patient_username, current_date);
    }

    public void onClickBtnCancel(View view) { finish(); }

    public void onClickBtnDone(View view) {
        presenter.uploadNewTodolist(currentTodolistModel, currentTodolist);
    }

    public void onClickBtnAddTodolistItem(View view) {
        Intent intent = new Intent(this, CreateATodolistItemActivity.class);
        intent.putExtra("patient_username", patient_username);
        intent.putExtra("current_date",current_date);
        startActivityForResult(intent, 1905);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1905 && resultCode == RESULT_OK) {
            if (data.hasExtra("content")) {
                String content = data.getStringExtra("content");

                TodolistItemModel model = new TodolistItemModel(Integer.toString(currentTodolist.size()),
                        current_listID, content, 0);
                currentTodolist.add(model);
                todolistItemAdapter.notifyDataSetChanged();

                /*
                List<TodolistItemModel> newTodolist = new ArrayList<>();
                newTodolist.addAll(currentTodolist);
                newTodolist.add(model);
                todolistItemAdapter.updateDataset(newTodolist);
                 */
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}