package com.example.onlinetherapist;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.sip.SipSession;
import android.util.Log;
import android.widget.Toast;


import androidx.annotation.NonNull;

import com.example.onlinetherapist.Login.Admin;
import com.example.onlinetherapist.Login.Patient;
import com.example.onlinetherapist.Login.UI.LoginActivity;
import com.example.onlinetherapist.MedicalRecord.MedicalRecordModel;
import com.example.onlinetherapist.appointment.ViewAppointmentActivity;
import com.example.onlinetherapist.account.IRegisterPresenter;
import com.example.onlinetherapist.appointment.IViewAppointmentPresenter;
import com.example.onlinetherapist.appointment.TimeSlotModel;
import com.example.onlinetherapist.homescreen.HomeActivity;
import com.example.onlinetherapist.noteadvice.TodolistItemModel;
import com.example.onlinetherapist.noteadvice.TodolistModel;
import com.example.onlinetherapist.homescreen.therapist.TherapistHomeActivity;
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

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.SimpleFormatter;


import com.example.onlinetherapist.appointment.TimeSlotModel;
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
    public void getPatientInfo(final onReadDataListener listener,String uname)
    {
        listener.onStart();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                snapshot = snapshot.child(Constant.PATIENT_TABLE);
                Patient patient = null;
                for(DataSnapshot traverse: snapshot.getChildren()){
                    if(traverse.child("username").getValue().toString().equals(uname)) {
                        String dob;
                        long height;
                        String password;
                        long sex;
                        String username;
                        long weight;
                        String fcm;

                        dob = traverse.child("dob").getValue().toString();
                        height = (long) traverse.child("height").getValue();
                        password = (String) traverse.child("password").getValue();
                        sex = (long) traverse.child("sex").getValue();
                        username = traverse.child("username").getValue().toString();
                        weight = (long) traverse.child("weight").getValue();
                        fcm = (String) traverse.child("fcm").getValue();
                        patient = new Patient(dob, height, password, sex, username, weight, fcm);
                        break;
                    }
                }
                listener.onSuccessGetPatientInfo(patient);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    public void getTimeSlot(final onReadDataListener listener)
    {
        listener.onStart();
        ArrayList<TimeSlotModel> timeSlotModelArray =  new ArrayList<TimeSlotModel>();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                snapshot = snapshot.child(Constant.APPOINTMENT_TABLE);

                for(DataSnapshot traverse: snapshot.getChildren()){
                    String userId = "";
                    long slot = 0;
                    long status = 0;
                    String date;

                    userId = traverse.child("User_ID").getValue().toString();
                    slot = (long) traverse.child("Slot").getValue();
                    status = (long)traverse.child("Status").getValue();
                    date = traverse.child("Date").getValue().toString();
                    timeSlotModelArray.add(new TimeSlotModel(date,slot,status,userId));
                }
                listener.onSuccessTimeSlot(timeSlotModelArray);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


    };
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
                    if(userIdDB.equals(userID) && status == 1){
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
    public void addNewAppointment(TimeSlotModel timeSlotModel,final onReadDataListener listener)
    {
        listener.onStart();
        databaseReference.child(Constant.APPOINTMENT_TABLE).push().setValue(timeSlotModel).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                listener.onSuccessAddNewAppointment();
            }
        });
    }
    public void cancelAppointment( final String userID, final onReadDataListener onReadDataListener, final String Date) {
        onReadDataListener.onStart();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                snapshot = snapshot.child(Constant.APPOINTMENT_TABLE);
                for(DataSnapshot traverse: snapshot.getChildren()){
                    String userIdDB = "";
                    userIdDB = traverse.child("User_ID").getValue().toString();
                    String appointmentDate =" ";
                    appointmentDate = traverse.child("Date").getValue().toString();
                    if(userIdDB.equals(userID) && appointmentDate.equals(Date)){
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
    public void removeAppointment( final String userID, final onReadDataListener onReadDataListener) {

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
                        databaseReference.child(Constant.APPOINTMENT_TABLE).
                                child(traverse.getKey().toString()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                onReadDataListener.onSuccess(finalSnapshot, "Success Remove");
                            }
                        });
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
                        else {
                            Toast.makeText(activity, "Incorrect password", Toast.LENGTH_SHORT).show();
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
//                            Toast.makeText(activity, "Log in successful", Toast.LENGTH_SHORT).show();
                            SendFCMTokenTherapist(activity, p.getUsername());
                            //break;
                        }
                        else {
                            Toast.makeText(activity, "Incorrect password", Toast.LENGTH_SHORT).show();
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
                Intent intent = new Intent(activity.getApplicationContext(), TherapistHomeActivity.class);
                intent.putExtra("fcm_token", token);
                activity.startActivity(intent);
                activity.finish();
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
                    editor.putBoolean("remember",false);
//                    editor.putString("remember and login","false");
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
                editor.putBoolean("remember",false);
//                editor.putString("remember and login","false");
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

    // lấy medical record của 1 patient
    public void getMedicalRecords (String patientID, onReadDataListener listener){
        listener.onStart();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DataSnapshot recordInfo = null;
                snapshot = snapshot.child(Constant.MEDICAL_RECORD_TABLE);
                for(DataSnapshot traverse: snapshot.getChildren()){
                    String patientIdDB = "";
                    patientIdDB = traverse.child(Constant.RECORD_PATIENT_ID).getValue().toString();
                    if(patientID.equals(patientIdDB)){
                        recordInfo = traverse;
                        listener.onSuccess(recordInfo,"One element");
                    }
                }
                listener.onSuccess(null,"Done");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onFailed(error);
            }
        });
    }

    public void getNotes(final String attri, final onReadDataListener listener){
        getNotes(attri,listener,"User_ID");
    }

    public void getNotes (final String attri, final onReadDataListener listener, String attrDB ) {
        listener.onStart();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DataSnapshot noteInformation = null;
                snapshot = snapshot.child(Constant.NOTE_TABLE);
                for(DataSnapshot traverse: snapshot.getChildren()){
                    String attriVal = "";
                    attriVal = traverse.child(attrDB).getValue().toString();
                    long status = (long)traverse.child("Status").getValue();
                    if(attriVal.equals(attri)) {
                        noteInformation = traverse;
                        listener.onSuccess(noteInformation, "One element");
                    }
                }
                listener.onSuccess(null, "Done");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onFailed(error);
            }
        });
    }

    public void getTodolists (final String userID, final onReadDataListener listener) {
        listener.onStart();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DataSnapshot todolistInformation = null;
                snapshot = snapshot.child(Constant.TODOLIST_TABLE);
                for(DataSnapshot traverse: snapshot.getChildren()){
                    String userIdDB = "";
                    userIdDB = traverse.child("User_ID").getValue().toString();
                    if(userIdDB.equals(userID)) {
                        todolistInformation = traverse;
                        listener.onSuccess(todolistInformation, "One element");
                    }
                }
                listener.onSuccess(null, "Done");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onFailed(error);
            }
        });
    }

    public void getTodolistItems (final String listID, final onReadDataListener listener) {
        listener.onStart();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DataSnapshot itemInformation = null;
                snapshot = snapshot.child(Constant.TODOLISTITEM_TABLE);
                for(DataSnapshot traverse: snapshot.getChildren()){
                    String listIdDB = "";
                    listIdDB = traverse.child("List_ID").getValue().toString();
                    if(listIdDB.equals(listID)) {
                        itemInformation = traverse;
                        listener.onSuccess(itemInformation, "One element");
                    }
                }
                listener.onSuccess(null, "Done");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onFailed(error);
            }
        });
    }

    public void setTodolistItemStatus (TodolistItemModel model, int newStatusValue, onSetValueListener listener) {
        //onReadDataListener is a bit ridiculous... but works.
        Map<String, Object> model_info = new HashMap<>();
        model_info.put("Status", newStatusValue);
        databaseReference.child(Constant.TODOLISTITEM_TABLE).child(model.getList_id()+"_"+model.getId()).updateChildren(model_info)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        listener.onSuccess("Success");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        listener.onFailed(e, "Failure");
//                        progressDialog.dismiss();
                    }
                });
    }

    // up 1 medical record ms
    public void pushNewMedicalRecord(
            String therapistID,
            String patientID,
            String created_date,
            String problems,
            String diagnosis,
            String treatment,
            String noteID,
            String todoListID,
            onSetValueListener listener
    ){
        String timestamp = Long.toString(System.currentTimeMillis());
        String generated_id = timestamp+"_"+patientID;

        Map<String,Object> info = new HashMap<>();
        info.put(Constant.RECORD_ID,generated_id);
        info.put(Constant.RECORD_THERAPIST_ID, therapistID);
        info.put(Constant.RECORD_PATIENT_ID,patientID);
        info.put(Constant.RECORD_DATE,created_date);
        info.put(Constant.RECORD_PROBLEM,problems);
        info.put(Constant.RECORD_DIAGNOSIS,diagnosis);
        info.put(Constant.RECORD_TREATMENT,treatment);
        info.put(Constant.RECORD_NOTE_ID, noteID);
        info.put(Constant.RECORD_TODOLIST_ID, todoListID);

        databaseReference.child(Constant.MEDICAL_RECORD_TABLE).child(generated_id).setValue(info)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        listener.onSuccess("Success");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        listener.onFailed(e,"Failed");
                    }
                });

    }
    public void newNote (String created_date, String content, String user_id, onSetValueListener listener) {
        //First, get an id that didn't exists
        //How to get an id that is guaranteed to be non-existent?
        //Use the trick: The timestamp + username --> 99.99999...% to be unique.
        String timestamp = Long.toString(System.currentTimeMillis());
        String generated_id = timestamp + '_' + user_id;

        Map<String, Object> info = new HashMap<>();
        info.put("User_ID", user_id);
        info.put("Status", 0);
        info.put("ID",generated_id);
        info.put("Date",created_date);
        info.put("Content", content);


        databaseReference.child(Constant.NOTE_TABLE).child(generated_id).setValue(info)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        listener.onSuccess(generated_id);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        listener.onFailed(e, "Failed");
                    }
                });

    }

    public void newTodolist (TodolistModel todolist, List<TodolistItemModel> todolistItems, onSetValueListener listener) {

        Map<String, Object> info = new HashMap<>();

        for (TodolistItemModel item : todolistItems) {
            info.clear();
            info.put("ID", item.getId());
            info.put("List_ID", item.getList_id());
            info.put("Content", item.getContent());
            info.put("Status", item.getStatus());

            databaseReference.child(Constant.TODOLISTITEM_TABLE).child(item.getList_id()+"_"+item.getId()).setValue(info)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            listener.onSuccess("todolistitem_sucess");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            listener.onFailed(e, "todolistitem_failure");
                        }
                    });
        }

        info.clear();
        info.put("ID", todolist.getId());
        info.put("Created_Date", todolist.getCreated_dateString());
        info.put("User_ID", todolist.getUser_id());


        databaseReference.child(Constant.TODOLIST_TABLE).child(todolist.getId()).setValue(info)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        listener.onSuccess("todolist_sucess");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        listener.onFailed(e, "todolist_failure");
                    }
                });

    }
}
