package com.example.onlinetherapist.MedicalRecord.patient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.onlinetherapist.MedicalRecord.MedicalRecordModel;
import com.example.onlinetherapist.R;
import com.example.onlinetherapist.noteadvice.NoteModel;
import com.example.onlinetherapist.noteadvice.patient.NoteAdvicePatientActivity;
import com.example.onlinetherapist.noteadvice.patient.ViewANoteActivity;
import com.example.onlinetherapist.noteadvice.patient.ViewATodolistActivity;

public class ViewMedicalRecordActivity extends AppCompatActivity implements IViewMedicalRecordContract.View{

    private IViewMedicalRecordContract.Presenter presenter;

    private TextView tvTherapist, tvPatient, tvDate;
    private EditText editProblem, editDiagnosis, editTreatment;
    private Button btnTodo, btnNote;

    private String noteID = "";
    private String todoListID = "";
    MedicalRecordModel medicalRecordModel;

    private NoteModel noteModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_medical_record);

        Log.d("AAA","hello");
        mapping();
        setListener();

        presenter = new ViewMedicalRecordPresenter(this);
        presenter.getMedicalRecordDetail();
        presenter.getRecordNoteDetail(noteID);

    }

    @Override
    public void getIntentData() {
        Intent intent = getIntent();
        medicalRecordModel = (MedicalRecordModel) intent.getSerializableExtra("record");
        noteID = medicalRecordModel.getNoteID();
        todoListID = medicalRecordModel.getTodoListID();
    }

    @Override
    public void assignNote(NoteModel noteModel) {
        this.noteModel = noteModel;
    }

    private void mapping() {
        tvTherapist = findViewById(R.id.tvTherapistname);
        tvPatient = findViewById(R.id.tvPatientname);
        tvDate = findViewById(R.id.tvDateCreated);
        editProblem = findViewById(R.id.editTextProblemDescription);
        editDiagnosis = findViewById(R.id.editTextDiagnosis);
        editTreatment = findViewById(R.id.editTextTreatment);
        btnTodo = findViewById(R.id.btnTodolist);
        btnNote = findViewById(R.id.btnNote);
        Button btnSave = findViewById(R.id.btnSave);

        editProblem.setFocusable(false);
        editDiagnosis.setFocusable(false);
        editTreatment.setFocusable(false);

        Log.d("AAA","sai ở đây hả ta");

        btnNote.setText("NOTE");
        btnTodo.setText("TO-DO LIST");
        btnSave.setVisibility(View.GONE);
    }

    private void setListener() {
        Log.d("AAA","im here");
        btnTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* TODO:
                    updated: record chứa id của note và todolist -> truyền id của tụi nó để đọc

                    vì mỗi medical record có 1 bộ advice riêng
                    nên fai lưu advice vs id của medical record,
                    truyền id medical record vào intent
                    nhớ sửa các class gọi intent tới NoteAdvicePatientActivity , note, todolist và cách fetch data
                */

                Log.d("AAA","inside todo");
                Intent intent = new Intent(ViewMedicalRecordActivity.this, ViewATodolistActivity.class);
//                intent.putExtra("recordID", recordID);
                intent.putExtra("id",todoListID);
                intent.putExtra("user_id",medicalRecordModel.getPatient());
                intent.putExtra("date",medicalRecordModel.getDate());
                startActivity(intent);
            }
        });

        btnNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("AAA","inside note");

                Intent intent = new Intent(ViewMedicalRecordActivity.this, ViewANoteActivity.class);
                intent.putExtra("date",noteModel.getDateString());
                intent.putExtra("content",noteModel.getContent());
                startActivity(intent);
            }
        });

    }


    @Override
    public void setMedicalRecordDetail() {
        tvTherapist.setText(medicalRecordModel.getTherapist());
        tvPatient.setText(medicalRecordModel.getPatient());
        tvDate.setText(medicalRecordModel.getDate());

        editProblem.setText(medicalRecordModel.getProblems());
        editDiagnosis.setText(medicalRecordModel.getDiagnosis());
        editTreatment.setText(medicalRecordModel.getTreatment());
    }
}