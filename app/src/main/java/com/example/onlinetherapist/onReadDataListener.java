package com.example.onlinetherapist;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public interface onReadDataListener {
    void onStart();
    void onSuccess(DataSnapshot data, String message);
    void onFailed(DatabaseError e);
}
