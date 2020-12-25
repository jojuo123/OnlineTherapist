package com.example.onlinetherapist.noteadvice.patient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.onlinetherapist.R;
import com.example.onlinetherapist.noteadvice.TodolistItemAdapter;
import com.example.onlinetherapist.noteadvice.TodolistItemModel;

import java.util.ArrayList;
import java.util.List;

public class ViewATodolistActivity extends AppCompatActivity implements IViewATodolistView {

    IViewATodolistPresenter presenter;
    String id, user_id;
    String created_date;

    ListView listViewTodolist;
    TodolistItemAdapter todolistItemAdapter;
    TextView textViewDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_atodolist);

        initData();
        initVariables();
    }

    private void initVariables() {
        presenter = new ViewATodolistPresenter(this, id);
        todolistItemAdapter=new TodolistItemAdapter(this,R.layout.listitem_todolistitem, new ArrayList<>());
        todolistItemAdapter.setPresenter(presenter);
        listViewTodolist=findViewById(R.id.listViewTodolist);
        listViewTodolist.setAdapter(todolistItemAdapter);

        textViewDate = findViewById(R.id.todolistDate);
        textViewDate.setText(created_date);
        presenter.retrieveTodolistItem();
    }

    private void initData() {
        id=getIntent().getStringExtra("id");
        user_id=getIntent().getStringExtra("user_id");
        created_date=getIntent().getStringExtra("date");
        /*
        n=getIntent().getIntExtra("n", 0);
        todolistItemModelList = new ArrayList<>();

        for (int j=0; j<n; ++j) {
            String key = "item" + Integer.toString(j);
            String content = getIntent().getStringExtra(key+"_content");
            String id = getIntent().getStringExtra(key+"_id");
            String list_id = getIntent().getStringExtra(key+"_listid");
            int status = getIntent().getIntExtra(key+"_status", 0);
            todolistItemModelList.add(new TodolistItemModel(id,list_id,content,status));
        }
        */
    }

    @Override
    public void updateTodolistItems(List<TodolistItemModel> newDataset) {
        todolistItemAdapter.updateDataset(newDataset);
    }
}