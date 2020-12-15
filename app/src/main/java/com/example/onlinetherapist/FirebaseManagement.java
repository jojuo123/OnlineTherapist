package com.example.onlinetherapist;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;


import androidx.annotation.NonNull;

import com.example.onlinetherapist.Login.Admin;
import com.example.onlinetherapist.Login.Patient;
import com.example.onlinetherapist.Login.UI.LoginActivity;
import com.example.onlinetherapist.appointment.ViewAppointmentActivity;
import com.example.onlinetherapist.account.IRegisterPresenter;
import com.example.onlinetherapist.appointment.IViewAppointmentPresenter;
import com.example.onlinetherapist.appointment.TimeSlotModel;
import com.example.onlinetherapist.homescreen.HomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.SimpleFormatter;

import static android.content.Context.MODE_PRIVATE;

public class FirebaseManagement {
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;
    boolean LoggedOut = false;
    boolean FlagLoggout;




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
    public void getUsernameData(final onReadDataListener onReadDataListener){
        onReadDataListener.onStart();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                onReadDataListener.onSuccess(snapshot.child(Constant.PATIENT_TABLE), "Success");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                onReadDataListener.onFailed(error);
            }
        });

    }
//    public void check_username(final Activity activity, final String username, final String password,
//                               final int sex, final int height, final int weight, final String dob){
//
////        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
//        progressDialog = new ProgressDialog(activity);
//        progressDialog.setMessage("Please wait...");
//        progressDialog.show();
//        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.child("Patients").hasChild(username)){
//                    Toast.makeText(activity,"Username Already Exists. Please Try Again!",
//                            Toast.LENGTH_SHORT).show();
//                    progressDialog.dismiss();
//                    return;
//                }
//                store_user_infor(activity, username, password, sex, height, weight, dob);
//
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//
//    }
    public void getBookAppointments(final String userID, final onReadDataListener listener){
        listener.onStart();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DataSnapshot appointmentInformation = null;
                snapshot = snapshot.child(Constant.APPOINTMENT_TABLE);
                for(DataSnapshot traverse: snapshot.getChildren()){
                    String userIdDB = "";
                    userIdDB = traverse.child("User_ID").getValue().toString();
                    long status = (long)traverse.child("Status").getValue();
                    if(userIdDB.equals(userID) && status == 0){
                        appointmentInformation = traverse;
                        break;
                    }
                }


                listener.onSuccess(appointmentInformation, "Success");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onFailed(error);
            }
        });

    }

    public void removeAppointment(final Date currentDate, final String userID, final onReadDataListener onReadDataListener) {
        onReadDataListener.onStart();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                snapshot = snapshot.child(Constant.APPOINTMENT_TABLE);
                for(DataSnapshot traverse: snapshot.getChildren()){
                    String userIdDB = "";
                    userIdDB = traverse.child("User_ID").getValue().toString();
                    if(userIdDB.equals(userID)){
                        final DataSnapshot finalSnapshot = traverse;
                        //calculate different time in order to allow cancellation
                        String bookedDate = traverse.child("Date").getValue().toString();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        Date bookDate = null;
                        try{
                            bookDate = simpleDateFormat.parse(bookedDate);
                        }
                        catch (ParseException e){
                            e.printStackTrace();
                        }
                        long diff = currentDate.getTime() - bookDate.getTime();
                        if(diff/(1000*60*60) <= 48){
                            onReadDataListener.onSuccess(finalSnapshot,
                                    "Failed");
                            return;
                        }

                        databaseReference.child(Constant.APPOINTMENT_TABLE).child(traverse.getKey().toString())
                                .child("Status").setValue(2).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                onReadDataListener.onSuccess(finalSnapshot, "Success Remove");
                            }
                        });


                        return;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                onReadDataListener.onFailed(error);
            }
        });
    }
    public void store_user_infor(final IRegisterPresenter.onRegistrationListener onRegistrationListener,
                                 String username, String password, int sex, int height, int weight, String dob) {
//        DatabaseReference databaseReference;
//        databaseReference = FirebaseDatabase.getInstance().getReference();
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
//                        progressDialog.dismiss();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        onRegistrationListener.onFailed("Failed Create Account. Please Check Internet Connection");
//                        progressDialog.dismiss();
                    }
                });
    }

    public void doSignIn(final Activity activity, final String username, final String password) {

        //final String Loginstate="Login";
        Query query= FirebaseDatabase.getInstance().getReference(Constant.PATIENT_TABLE)
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

//                            Intent intent = new Intent(activity, HomeActivity.class);
//                            activity.startActivity(intent);
                            SendFCMTokenPatient(activity, p.getUsername());
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
        Query query= FirebaseDatabase.getInstance().getReference(Constant.THERAPIST_TABLE)
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
                            SendFCMTokenTherapist(activity, p.getUsername());
                            //break;
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
//    public void checkActive(final Activity activity, final String username, final String state){
//        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.child("Active").hasChild(username)){//This account is Online and one can log out
//                    if(state.equals("Login")) {
//                        Toast.makeText(activity, "Username Already Login. Please Use Another Account!",
//                                Toast.LENGTH_SHORT).show();
//                    }
//                    else if(state.equals("Logout")){
//                        databaseReference.child("Active").child(username).setValue(null);
//                        //remove account from Active-->make it Offline.
//                    }
//                    else{
//                        Toast.makeText(activity, "Illegal State",
//                                Toast.LENGTH_SHORT).show();
//                    }
//                }
//                //This account is Offline and one can log in
//                else {
//                    if (state.equals("Login")) {
//                        databaseReference.child("Active").child(username).push().setValue("On");
//                        Intent intent = new Intent(activity, MainActivity.class);
//                        activity.startActivity(intent);
//                    }
//                    else if (state.equals("Logout")) {
//                        Toast.makeText(activity, "Username are not login. These must be bug!",
//                                Toast.LENGTH_SHORT).show();
//                        //remove account from Active-->make it Offline.
//                    }
//                    else {
//                        Toast.makeText(activity, "Illegal State",
//                                Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }



    public void SendFCMTokenPatient(final String uname)
    {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (!task.isSuccessful())
                {
                    Log.e("FCM", "fail to fetch fcm");
                    return;
                }
                String token = task.getResult();
                SendFCMTokenPatient(uname, token);
            }
        });
    }

    public void SendFCMTokenPatient(String uname, String token)
    {
        DatabaseReference databaseReference;
        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child(Constant.PATIENT_TABLE).child(uname).child(Constant.FCM_TOKEN).setValue(token);
    }

    public void SendFCMTokenPatient(final Activity activity, final String uname)
    {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (!task.isSuccessful())
                {
                    Log.e("FCM", "fail to fetch fcm");
                    return;
                }
                String token = task.getResult();
                SendFCMTokenPatient(activity, uname, token);
            }
        });
    }

    public void SendFCMTokenPatient(final Activity activity, String uname, String token)
    {
        DatabaseReference databaseReference;
        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child(Constant.PATIENT_TABLE).child(uname).child(Constant.FCM_TOKEN).setValue(token).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Intent intent = new Intent(activity.getApplicationContext(), HomeActivity.class);
                intent.putExtra("fcm_token", token);
                activity.startActivity(intent);
                activity.finish();
            }
        });
    }

    public void SendFCMTokenTherapist(final Activity activity, final String uname)
    {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if (!task.isSuccessful())
            {
                Log.e("FCM", "fail to fetch fcm");
                return;
            }
            String token = task.getResult();
            SendFCMTokenTherapist(activity, uname, token);
        });
    }

    public void SendFCMTokenTherapist(final Activity activity, String uname, String token)
    {
        DatabaseReference databaseReference;
        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child(Constant.THERAPIST_TABLE).child(uname).child(Constant.FCM_TOKEN).setValue(token).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
//                Intent intent = new Intent(activity.getApplicationContext(), HomeActivity.class);
//                intent.putExtra("fcm_token", token);
//                activity.startActivity(intent);
//                activity.finish();
            }
        });
    }

    public interface ILogoutFB
    {
        void onSuccess();
        void onFailure();
    }

    public class LogoutClass
    {
        public void PatientLogout(final Activity activity, String uname, final FirebaseManagement.ILogoutFB listener)
        {
            DatabaseReference databaseReference;
            Log.e("name", uname);
            databaseReference = FirebaseDatabase.getInstance().getReference();
            databaseReference.child(Constant.PATIENT_TABLE).child(uname).child(Constant.FCM_TOKEN).setValue(null).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    listener.onSuccess();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    listener.onFailure();
                }
            }).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Log.d("success", "LO suc");
                    SharedPreferences preferences = activity.getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putString("remember","false");
                    editor.putString("remember and login","false");
                    editor.apply();
                    activity.startActivity(new Intent(activity.getApplicationContext(), LoginActivity.class));
                    activity.finish();
                }
            });
        }
    }

    public boolean PatientLogout(final Activity activity, String uname) {
        LoggedOut = false;
        FlagLoggout = false;
        new LogoutClass().PatientLogout(activity, uname, new ILogoutFB() {
            @Override
            public void onSuccess() {
                LoggedOut = true;
//                Log.d("success", "LO suc");
//                SharedPreferences preferences = activity.getSharedPreferences("checkbox", MODE_PRIVATE);
//                SharedPreferences.Editor editor=preferences.edit();
//                editor.putString("remember","false");
//                editor.putString("remember and login","false");
//                editor.apply();
//                activity.startActivity(new Intent(activity.getApplicationContext(), LoginActivity.class));
//                activity.finish();
            }

            @Override
            public void onFailure() {
                LoggedOut = false;
                Log.d("fail", "LO fai");
                FlagLoggout = true;
            }
        });
        Log.d("return", String.valueOf(LoggedOut));
        return LoggedOut;
    }

    public void TherapistLogout(final Activity activity, String uname)
    {
        DatabaseReference databaseReference;
        Log.e("name", uname);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child(Constant.THERAPIST_TABLE).child(uname).child(Constant.FCM_TOKEN).setValue(null).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                //Log.d("success", "LO suc");
                SharedPreferences preferences = activity.getSharedPreferences("checkbox", MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences.edit();
                editor.putString("remember","false");
                editor.putString("remember and login","false");
                editor.apply();
                activity.startActivity(new Intent(activity.getApplicationContext(), LoginActivity.class));
                activity.finish();
            }
        });
    }

    public void ViewAppointmentPatient(final Activity activity)
    {

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child(Constant.THERAPIST_TABLE).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren())
                {
                    if (ds.exists())
                    {
                        Admin ad = ds.getValue(Admin.class);
                        Intent intent = new Intent(activity.getApplicationContext(), ViewAppointmentActivity.class);
                        intent.putExtra("therapistname", ad.getUsername());
                        intent.putExtra("therapistfcm", ad.getFcm());
                        activity.startActivity(intent);
                        //break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
