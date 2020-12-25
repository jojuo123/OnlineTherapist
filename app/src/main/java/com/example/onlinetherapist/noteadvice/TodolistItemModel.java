package com.example.onlinetherapist.noteadvice;

public class TodolistItemModel {
    public TodolistItemModel(String id, String list_id, String content, int status) {
        this.id = id;
        this.list_id = list_id;
        this.content = content;
        this.status = status;
    }


    String id, list_id, content;
    int status;

    public String getId() {
        return id;
    }

    public String getList_id() {
        return list_id;
    }

    public String getContent() {
        return content;
    }

    public int getStatus() {
        return status;
    }
}
