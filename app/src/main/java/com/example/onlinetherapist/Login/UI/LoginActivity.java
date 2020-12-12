package com.example.onlinetherapist.Login.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.example.onlinetherapist.MainActivity;
import com.example.onlinetherapist.R;

public class LoginActivity extends AppCompatActivity implements ILoginView{
    private CheckBox saveLoginCheckBox;
    private TextView ChangeAuthority;
    private Button LoginButton,RegButton;
    private EditText username, password;
    private int flag=0;
    LoginPresenter presenter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        presenter=new LoginPresenter();
        LogInUser();

        SharedPreferences preferences=getSharedPreferences("checkbox",MODE_PRIVATE);
        String checkbox= preferences.getString("remember and login","");
        if(checkbox.equals("true")){
            navigatetoMainMenu();
        }
        else{
            Toast.makeText(this,"Sign in",Toast.LENGTH_SHORT).show();
        }

        checkRememberBox();
        onClickLogin();
        onAuthorizeChange();
    }


    private void onAuthorizeChange() {
        ChangeAuthority=findViewById(R.id.changeAuthor);
        RegButton=findViewById(R.id.register);
        ChangeAuthority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag==0){
                    ChangeAuthority.setText("Log in as user");
                    flag=1;
                    RegButton.setVisibility(View.GONE);
                }
                else{
                    ChangeAuthority.setText("Log in as administrator");
                    RegButton.setVisibility(View.VISIBLE);
                    flag=0;
                }
            }
        });
    }
    public void LogInUser() {
        username=findViewById(R.id.editTextUsername);
        password=findViewById(R.id.editTextPassword);
        saveLoginCheckBox=findViewById(R.id.rememberlog);
        LoginButton= findViewById(R.id.login);
        RegButton=findViewById(R.id.register);
        ChangeAuthority= findViewById(R.id.changeAuthor);
    }

    @Override
    public void LogInAdmin() {
        //setContentView(R.layout.activity_admin__login);
        username=findViewById(R.id.editTextUsername);
        password=findViewById(R.id.editTextPassword);
        saveLoginCheckBox=findViewById(R.id.rememberlog);
        LoginButton= findViewById(R.id.login);
        ChangeAuthority= findViewById(R.id.changeAuthor);
    }

    private void checkRememberBox(){
        saveLoginCheckBox=findViewById(R.id.rememberlog);
        saveLoginCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()){
                    SharedPreferences preferences=getSharedPreferences("checkbox",MODE_PRIVATE);
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putString("remember","true");
                    editor.apply();
                    Toast.makeText(LoginActivity.this,"Checked",Toast.LENGTH_SHORT).show();
                }
                else if(!compoundButton.isChecked()){
                    SharedPreferences preferences=getSharedPreferences("checkbox",MODE_PRIVATE);
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putString("remember","false");
                    editor.apply();
                    Toast.makeText(LoginActivity.this,"Unchecked",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private void onClickLogin() {
        LoginButton= findViewById(R.id.login);
        LoginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(flag==0) LogInUser();
                else LogInAdmin();
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
                saveUserName(log_username);
                if(flag==0) presenter.authorize(LoginActivity.this,log_username,log_password);
                else{
                    presenter.Adminauthorize(LoginActivity.this,log_username,log_password);
                }
            }
        });
    }
    public void saveUserName(String username){
        SharedPreferences savedUsername=getSharedPreferences("SavedUsername",MODE_PRIVATE);
        SharedPreferences.Editor editor=savedUsername.edit();
        editor.putString("username",username);
        editor.apply();
    }




    @Override
    public void navigatetoMainMenu() {
        startActivity(new Intent(this, MainActivity.class));
    }


}
