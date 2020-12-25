package com.example.onlinetherapist;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public interface onSetValueListener {
    void onSuccess(String message);
    void onFailed(Exception e, String message);
}
