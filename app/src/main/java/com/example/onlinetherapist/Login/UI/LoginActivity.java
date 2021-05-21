package com.example.onlinetherapist.Login.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.example.onlinetherapist.Login.LoginPresenter;
import com.example.onlinetherapist.R;
import com.example.onlinetherapist.account.RegisterActivity;
import com.example.onlinetherapist.homescreen.HomeActivity;
import com.example.onlinetherapist.homescreen.therapist.TherapistHomeActivity;

public class LoginActivity extends AppCompatActivity implements ILoginView{
    private CheckBox saveLoginCheckBox;
    private TextView ChangeAuthority;
    private Button LoginButton,RegButton;
    private EditText username, password;
    private boolean isTherapistUser = false;
    LoginPresenter presenter;
    private boolean saveLoginStatus = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        presenter=new LoginPresenter();
        setupLogin();
    }

    public void mappingLayout(){
        username=findViewById(R.id.editTextUsername);
        password=findViewById(R.id.editTextPassword);
        saveLoginCheckBox=findViewById(R.id.rememberlog);
        LoginButton= findViewById(R.id.login);
        RegButton=findViewById(R.id.register);
        ChangeAuthority= findViewById(R.id.changeAuthor);
    }

    @Override
    public void setupLogin() {
        mappingLayout();
        checkSaveLogin();
        checkRememberBox();
        onClickLogin();
        onAuthorizeChange();
    }

    @Override
    public void navigatetoMainMenu() {
        String Username;
        SharedPreferences saved=getSharedPreferences("SavedUsername", MODE_PRIVATE);
        Username = saved.getString("username","");
        if (Username.isEmpty()) return;
        //presenter.navigateToMainMenu(this, Username);
        if(isTherapistUser)
            startActivity(new Intent(this, TherapistHomeActivity.class));
        else
            startActivity(new Intent(this,HomeActivity.class));
        finish();
    }

    private void checkSaveLogin(){
        SharedPreferences preferences=getSharedPreferences("checkbox",MODE_PRIVATE);
        Boolean checkbox= preferences.getBoolean("remember",false);
        if(checkbox){
            isTherapistUser = preferences.getBoolean("therapist",false);
            navigatetoMainMenu();
        }
    }

    private void onAuthorizeChange() {
//        ChangeAuthority=findViewById(R.id.changeAuthor);
//        RegButton=findViewById(R.id.register);
        ChangeAuthority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isTherapistUser){
                    Log.d("AAA","admin ne");
                    ChangeAuthority.setText("Log in as User");
                    isTherapistUser =true;
//                    Toast.makeText(LoginActivity.this,"Successful change authorize to Admin",Toast.LENGTH_SHORT).show();
                    RegButton.setVisibility(View.GONE);
                }
                else{
                    Log.d("AAA","user ne");
                    ChangeAuthority.setText("Log in as Administrator");
                    RegButton.setVisibility(View.VISIBLE);
//                    Toast.makeText(LoginActivity.this,"Successful change authorize to User",Toast.LENGTH_SHORT).show();
                    isTherapistUser =false;
                }
            }
        });
    }


//    public void LogInUser() {
//        username=findViewById(R.id.editTextUsername);
//        password=findViewById(R.id.editTextPassword);
//        saveLoginCheckBox=findViewById(R.id.rememberlog);
//        LoginButton= findViewById(R.id.login);
//        RegButton=findViewById(R.id.register);
//        ChangeAuthority= findViewById(R.id.changeAuthor);
//    }
//
//    @Override
//    public void LogInAdmin() {
//        //setContentView(R.layout.activity_admin__login);
//        username=findViewById(R.id.editTextUsername);
//        password=findViewById(R.id.editTextPassword);
//        saveLoginCheckBox=findViewById(R.id.rememberlog);
//        LoginButton= findViewById(R.id.login);
//        ChangeAuthority= findViewById(R.id.changeAuthor);
//    }

    private void checkRememberBox(){
//        saveLoginCheckBox=findViewById(R.id.rememberlog);
        saveLoginCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                saveLoginStatus=b;
//                if(compoundButton.isChecked()){
//                    SharedPreferences preferences=getSharedPreferences("checkbox",MODE_PRIVATE);
//                    SharedPreferences.Editor editor=preferences.edit();
//                    editor.putString("remember","true");
//                    editor.apply();
////                    Toast.makeText(LoginActivity.this,"Checked",Toast.LENGTH_SHORT).show();
//                }
//                else if(!compoundButton.isChecked()){
//                    SharedPreferences preferences=getSharedPreferences("checkbox",MODE_PRIVATE);
//                    SharedPreferences.Editor editor=preferences.edit();
//                    editor.putString("remember","false");
//                    editor.apply();
////                    Toast.makeText(LoginActivity.this,"Unchecked",Toast.LENGTH_SHORT).show();
//                }

            }
        });

    }

    private void onClickLogin() {
//        LoginButton= findViewById(R.id.login);
        LoginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
//                if(flag==0) LogInUser();
//                else LogInAdmin();
                String log_username, log_password;
                if(username.getText().length()==0){
                    Toast.makeText(LoginActivity.this,"Please input username",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(password.getText().length()==0){
                    Toast.makeText(LoginActivity.this,"Please input password",Toast.LENGTH_SHORT).show();
                    return;
                }
                log_username=username.getText().toString();
                log_password=password.getText().toString();

                //Toast.makeText(LoginActivity.this,log_username + " " + log_password,Toast.LENGTH_LONG).show();
                //
                saveLoginStatus();
                saveUserName(log_username);
                if(!isTherapistUser) {

                    Log.d("AAA","dang nhap user ne");
                    presenter.authorize(LoginActivity.this,log_username,log_password);
                }
                else{

                    Log.d("AAA","dang nhap admin ne");
                    presenter.Adminauthorize(LoginActivity.this,log_username,log_password);
                }
            }
        });
    }

    private void saveLoginStatus() {
        SharedPreferences preferences=getSharedPreferences("checkbox",MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
//        editor.putString("remember",String.valueOf(saveLoginStatus));
//        editor.putString("therapist",String.valueOf(isTherapistUser));
        editor.putBoolean("remember",saveLoginStatus);
        editor.putBoolean("therapist", isTherapistUser);

        editor.apply();
    }

    public void saveUserName(String username){
        SharedPreferences savedUsername=getSharedPreferences("SavedUsername",MODE_PRIVATE);
        SharedPreferences.Editor editor=savedUsername.edit();
        editor.putString("username",username);
        editor.apply();
    }

    public void onRegisterClick(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
    }
}
