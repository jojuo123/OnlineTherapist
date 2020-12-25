package com.example.onlinetherapist.noteadvice;

import java.util.ArrayList;
import java.util.List;

public class TodolistModel {
    public String getId() {
        return id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getCreated_dateString() {
        return created_date.toString();
    }

    public List<TodolistItemModel> getTodolist() {
        return todolist;
    }

    public void addTodolistItem (TodolistItemModel item) {
        todolist.add(item);
    }

    String id, user_id;
    String created_date;
    List<TodolistItemModel> todolist;

    public TodolistModel(String id, String user_id, String created_date) {
        this.id = id;
        this.user_id = user_id;
        this.created_date = created_date;
        todolist = new ArrayList<>();
    }

    public TodolistModel(String id, String user_id, String created_date, List<TodolistItemModel> todolist) {
        this.id = id;
        this.user_id = user_id;
        this.created_date = created_date;
        this.todolist = todolist;
    }


}
