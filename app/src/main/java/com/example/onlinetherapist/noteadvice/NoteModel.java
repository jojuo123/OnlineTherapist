package com.example.onlinetherapist.noteadvice;

public class NoteModel {
    String id, user_id, content;
    int status;
    String date; ///?????

    public String getId() {
        return id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getContent() {
        return content;
    }

    public int getStatus() {
        return status;
    }

    public String getDateString() {return date;}

    public NoteModel(String id, String user_id, String content, int status, String date) {
        this.id = id;
        this.user_id = user_id;
        this.content = content;
        this.status = status;
        this.date = date;
    }


}
