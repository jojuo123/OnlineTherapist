package com.example.onlinetherapist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.onlinetherapist.FirebaseManagement;

import com.example.onlinetherapist.Login.UI.LoginActivity;

public class MainActivity extends AppCompatActivity {
    private Button LogoutButton;
    private String username="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LogoutButton= findViewById(R.id.logout);


//        username=SavedCurrentUsername();
//
//        SharedPreferences preferences=getSharedPreferences("checkbox",MODE_PRIVATE);
//        String checkbox= preferences.getString("remember","");
//        if(checkbox.equals("true")){
//            SharedPreferences.Editor editor=preferences.edit();
//            editor.putString("remember and login","true");
//            editor.apply();
//        }
//        else{
//            SharedPreferences.Editor editor=preferences.edit();
//            editor.putString("remember and login","false");
//            editor.apply();
//        }
//
//
        LogoutButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String LogOutState="Logout";
//                FirebaseManagement myRef= new FirebaseManagement();
//
//                SharedPreferences preferences=getSharedPreferences("checkbox",MODE_PRIVATE);
//                SharedPreferences.Editor editor=preferences.edit();
//                editor.putString("remember","false");
//                editor.putString("remember and login","false");
//                editor.apply();
//
//                Intent intent= new Intent(MainActivity.this, LoginActivity.class);
//                startActivity(intent);
            }
        });
    }
    public String SavedCurrentUsername(){
        String Username;
        SharedPreferences saved=getSharedPreferences("SavedUsername",MODE_PRIVATE);
        Username= saved.getString("username","");
        return Username;
    }


}