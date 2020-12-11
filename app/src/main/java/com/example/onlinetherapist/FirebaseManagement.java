package com.example.onlinetherapist;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.onlinetherapist.Login.Admin;
import com.example.onlinetherapist.Login.Patient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class FirebaseManagement {
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;
    private static class SingletonHolder{
        private static final FirebaseManagement INSTANCE = new FirebaseManagement();
    }
    public static FirebaseManagement getInstance(){
        return SingletonHolder.INSTANCE;
    }
    public FirebaseManagement()
    {
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }
    public DatabaseReference getDatabaseReference(){
        return databaseReference;
    }
    public void check_username(final Activity activity, final String username, final String password,
                               final int sex, final int height, final int weight, final String dob){

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("Patients").hasChild(username)){
                    Toast.makeText(activity,"Username Already Exists. Please Try Again!",
                            Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    return;
                }
                store_user_infor(activity, username, password, sex, height, weight, dob);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
    public void store_user_infor(Activity activity, String username, String password, int sex, int height, int weight, String dob) {
        DatabaseReference databaseReference;
        databaseReference = FirebaseDatabase.getInstance().getReference();
        Map<String, Object> user_infor = new HashMap<>();
        user_infor.put("username", username);
        user_infor.put("password", password);
        user_infor.put("sex",sex);
        user_infor.put("height",height);
        user_infor.put("weight", weight);
        user_infor.put("dob", dob);

        databaseReference.child("Patients").child(username).setValue(user_infor)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
//                        onRegistrationListener.onSuccess("Success Create Account");
                        progressDialog.dismiss();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
//                        onRegistrationListener.onFailed("Failed Create Account. Please Check Internet Connection");
                        progressDialog.dismiss();
                    }
                });
    }
    public void doSignIn(final Activity activity, final String username, final String password) {
        Query query= FirebaseDatabase.getInstance().getReference("Patients")
                .orderByChild("username")
                .equalTo(username);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    for( DataSnapshot user : snapshot.getChildren()) {
                        Patient p = user.getValue(Patient.class);
                        assert p != null;
                        if (p.getPassword().equals(password)) {
                            Toast.makeText(activity, "Log in successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(activity, MainActivity.class);
                            activity.startActivity(intent);
                        }
                    }
                }
                else{
                    Toast.makeText(activity,"Log in failed. Please try again.",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void doSignInAdmin(final Activity activity, final String username, final String password) {
        Query query= FirebaseDatabase.getInstance().getReference("Therapists")
                .orderByChild("username")
                .equalTo(username);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    for( DataSnapshot user : snapshot.getChildren()) {
                        Admin p = user.getValue(Admin.class);
                        assert p != null;
                        if (p.getPassword().equals(password)) {
                            Toast.makeText(activity, "Log in successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(activity, MainActivity.class);
                            activity.startActivity(intent);
                        }
                    }
                }
                else{
                    Toast.makeText(activity,"Log in failed. Please try again.",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
