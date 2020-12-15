package com.example.onlinetherapist.account;

import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.onlinetherapist.FirebaseManagement;
import com.example.onlinetherapist.onReadDataListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterInteractor implements IRegisterPresenter.Interactor {
    private IRegisterPresenter.onRegistrationListener onRegistrationListener;
    ProgressDialog progressDialog;
    private FirebaseManagement firebaseManagement;
    public RegisterInteractor(IRegisterPresenter.onRegistrationListener onRegistrationListener){
        this.onRegistrationListener = onRegistrationListener;
        firebaseManagement = FirebaseManagement.getInstance();
    }

    @Override
    public void validate_infor(final Activity activity, final String username, final String password,
                               String cf_password, final int sex, final int height, final int weight, Calendar birth) {
        if (username.isEmpty() || password.isEmpty() || cf_password.isEmpty() || sex == -1 || String.valueOf(height).isEmpty()
                || String.valueOf(weight).isEmpty())
        {
            onRegistrationListener.onFailed("Please fill in all the information!");
            return;
        }
        if(username.length() < 6){
            onRegistrationListener.onFailed("Username is too short. Please try another one!");
            return;
        }
        if(password.length() < 8)
        {
            onRegistrationListener.onFailed("Password is too weak. Please try another one!");
            return;
        }
        if(!password.equals(cf_password)){
            onRegistrationListener.onFailed("Password and Confirm Password dont match!");
            return;
        }
        int year = birth.get(Calendar.YEAR);

        if(year >= 2008){
            onRegistrationListener.onFailed("Sorry. You need permission from parents!");
            return;
        }
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        final String dob = dateFormat.format(birth.getTime());
        //firebaseManagement.check_username(activity, username, password, sex, height, weight, dob);
        firebaseManagement.getUsernameData(new onReadDataListener() {
            ProgressDialog progressDialog = new ProgressDialog(activity);
            @Override
            public void onStart() {
                progressDialog.setMessage("Please Wait..");
                progressDialog.show();
            }

            @Override
            public void onSuccess(DataSnapshot data, String message) {
                if(data.child("Patients").hasChild(username)){
                    onRegistrationListener.onFailed("Username Already Exists. Please Try Again!");
                    progressDialog.dismiss();
                    return;
                }
                firebaseManagement.store_user_infor(onRegistrationListener, username, password, sex, height, weight, dob);
                progressDialog.dismiss();
            }

            @Override
            public void onFailed(DatabaseError e) {
                progressDialog.dismiss();
            }
        });
//        check_exist(activity, username, password, sex, height, weight, dob);
    }

    @Override
    public void upload_infor(Activity activity, String username, String password, int sex, int height, int weight, String dob) {
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
                        onRegistrationListener.onSuccess("Success Create Account");

                        progressDialog.dismiss();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        onRegistrationListener.onFailed("Failed Create Account. Please Check Internet Connection");
                        progressDialog.dismiss();
                    }
                });
    }

    @Override
    public void firebase_registration(Activity activity, String username, String password) {

    }

}
