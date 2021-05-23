package com.example.onlinetherapist.MedicalRecord.patient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.onlinetherapist.MedicalRecord.MedicalRecordAdapter;
import com.example.onlinetherapist.MedicalRecord.MedicalRecordModel;
import com.example.onlinetherapist.R;

import java.util.ArrayList;

public class MedicalRecordMainActivity extends AppCompatActivity implements IMedicalRecordMainContract.View{

    private IMedicalRecordMainContract.Presenter presenter;
    private RecyclerView recyclerView;
    private MedicalRecordAdapter medicalRecordAdapter;
    private String patientID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_record_main);

        presenter = new MedicalRecordMainPresenter(this);
        recyclerView = findViewById(R.id.recyclerViewMedicalRecords);
        fetchData();

    }

    private void fetchData() {
        Intent intent = getIntent();
        patientID = intent.getStringExtra("username");
        presenter.fetchAllMedicalRecords(patientID);
    }


    @Override
    public void setAllMedicalRecords(ArrayList<MedicalRecordModel> medicalRecordModelArrayList) {
        medicalRecordAdapter = new MedicalRecordAdapter(this,medicalRecordModelArrayList,R.layout.medical_record);
        recyclerView.setAdapter(medicalRecordAdapter);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
    }
}