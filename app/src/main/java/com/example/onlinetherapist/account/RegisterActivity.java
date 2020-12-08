package com.example.onlinetherapist.account;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.onlinetherapist.R;

import java.util.Calendar;
import java.util.Date;

public class RegisterActivity extends AppCompatActivity implements IRegisterPresenter.View{
    EditText reg_username, reg_password, reg_cf_password, reg_height, reg_weight;
    int reg_sex;
    DatePicker reg_dob;
    Button submit, redirect;
    ProgressDialog progressDialog;

    RegisterPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        presenter = new RegisterPresenter((IRegisterPresenter.View) this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");

        onClickedSubmit();

    }

    private void onClickedSubmit() {
        submit = (Button)findViewById(R.id.reg_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initViews();
                String username, pass, cf_pass;
                Calendar birth;
                int sex, height, weight;

                birth = get_date_picker();
                if(!checkValidate()){
                    onRegisterFailed("Please Fill All The Blank");
                    return;
                }

                username = reg_username.getText().toString();
                pass = reg_password.getText().toString();
                cf_pass = reg_cf_password.getText().toString();
                sex = reg_sex;
                height = Integer.parseInt(reg_height.getText().toString());
                weight = Integer.parseInt(reg_weight.getText().toString());


                init_register(username, pass, cf_pass, sex,
                        height, weight, birth);
            }
        });
    }

    private boolean checkValidate() {
        if(reg_username.getText().length() == 0){
            return false;
        }
        if(reg_password.getText().length() == 0){
            return false;
        }
        if(reg_cf_password.getText().length() == 0){
            return false;
        }

        if(reg_height.getText().length() == 0)
            return false;
        if(reg_weight.getText().length() == 0)
            return false;

        return true;
    }

    private void init_register(String username, String pass, String cf_pass, int sex,
                               int height, int weight, Calendar dob){
        presenter.register(RegisterActivity.this, username, pass, cf_pass, sex,
                height, weight, dob);
    }
    public Calendar get_date_picker(){
        int day = reg_dob.getDayOfMonth();
        int month = reg_dob.getMonth();
        int year =  reg_dob.getYear();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        return calendar;
    }

    @Override
    public void onRegisterFailed(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRegisterSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void initViews()
    {
        reg_username = (EditText)findViewById(R.id.reg_username);
        reg_password = (EditText)findViewById(R.id.reg_password);
        reg_cf_password = (EditText)findViewById(R.id.reg_cf_password);
        reg_height = (EditText)findViewById(R.id.reg_height);
        reg_weight = (EditText)findViewById(R.id.reg_weight);

        reg_dob = (DatePicker)findViewById(R.id.reg_date);

        redirect = (Button)findViewById(R.id.reg_back_login);


    }

    public void onGenderClick(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()){
            case R.id.sex_male:
                if(checked) {
                    reg_sex = 1;
                }
                break;
            case R.id.sex_female:
                if(checked) {
                    reg_sex = 0;
                }
                break;
            case  R.id.sex_others:
                if(checked) {
                    reg_sex = 2;
                }
                break;
            default:
                reg_sex = -1;
                break;
        }
    }
}