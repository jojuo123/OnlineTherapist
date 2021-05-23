package com.example.onlinetherapist.MedicalRecord.therapist;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlinetherapist.R;
import com.example.onlinetherapist.noteadvice.NoteModel;
import com.example.onlinetherapist.noteadvice.TodolistItemModel;
import com.example.onlinetherapist.noteadvice.TodolistModel;
import com.example.onlinetherapist.noteadvice.therapist.note.CreateANoteActivity;
import com.example.onlinetherapist.noteadvice.therapist.todoList.CreateATodolistActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CreateMedicalRecordActivity extends AppCompatActivity implements ICreateMedicalRecordContract.View{

    private static final int REQUEST_CODE_NOTE = 111;
    private static final int REQUEST_CODE_TODOLIST = 222;

    // activity
    private TextView tvTherapist, tvPatient, tvDate;
    private EditText editProblem, editDiagnosis, editTreatment;
    private Button btnTodo, btnNote, btnSave;
    private String therapistName, patientName, dateCreated;

    //note
    private String noteContent = "";
    private boolean addNote = false;

    // todolist
    private TodolistModel todolistModel = null;
    private ArrayList<TodolistItemModel> todolistItemModels = new ArrayList<>();
    private boolean addTodoList = false;


    private ICreateMedicalRecordContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_medical_record);

        presenter = new CreateMedicalRecordPresenter(this);
        mapping();
        fetchData();
        setListener();


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
        btnSave = findViewById(R.id.btnSave);
    }

    private void fetchData() {
        Intent intent = getIntent();
        patientName = intent.getStringExtra("patient_username");
        therapistName = SavedCurrentUsername();
        dateCreated = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());

        tvTherapist.setText(therapistName);
        tvPatient.setText(patientName);
        tvDate.setText(dateCreated);
    }

    private void setListener() {
        btnNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(
                        CreateMedicalRecordActivity.this,
                        CreateANoteActivity.class
                );

                intent.putExtra("patient_username", patientName);
                intent.putExtra("save",false);
                startActivityForResult(intent,REQUEST_CODE_NOTE);
            }
        });

        btnTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(
                        CreateMedicalRecordActivity.this,
                        CreateATodolistActivity.class
                );
                intent.putExtra("patient_username", patientName);
                intent.putExtra("save",false);
                startActivityForResult(intent,REQUEST_CODE_TODOLIST);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editProblem.getText().toString().isEmpty())
                    Toast.makeText(CreateMedicalRecordActivity.this, "Please fill in the problem", Toast.LENGTH_SHORT).show();
                else if(editDiagnosis.getText().toString().isEmpty())
                    Toast.makeText(CreateMedicalRecordActivity.this, "Please fill in the diagnosis", Toast.LENGTH_SHORT).show();
                else if(editTreatment.getText().toString().isEmpty())
                    Toast.makeText(CreateMedicalRecordActivity.this, "Please fill in the treatment", Toast.LENGTH_SHORT).show();
                else if(!addNote)
                    Toast.makeText(CreateMedicalRecordActivity.this, "Please add note", Toast.LENGTH_SHORT).show();
                else if(!addTodoList)
                    Toast.makeText(CreateMedicalRecordActivity.this, "Please add todo list", Toast.LENGTH_SHORT).show();
                else
                    presenter.pushTodoListToServer(todolistModel,todolistItemModels);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK && data!=null){
            if(requestCode==REQUEST_CODE_NOTE){
                noteContent = data.getStringExtra("content");
                addNote=true;
            }
            else if(requestCode==REQUEST_CODE_TODOLIST){
                todolistModel = (TodolistModel) data.getSerializableExtra("todolist");
                todolistItemModels = (ArrayList<TodolistItemModel>) data.getSerializableExtra("todoitemlist");
                addTodoList=true;
            }
        }
    }

    public String SavedCurrentUsername(){
        SharedPreferences saved = getSharedPreferences("SavedUsername", MODE_PRIVATE);
        String Username = saved.getString("username","");
        return Username;
    }


    @Override
    public void pushTodoListComplete() {
        presenter.pushNotesToServer(dateCreated,noteContent,patientName);
    }

    @Override
    public void pushNoteComplete(String noteID) {
        String problem = editProblem.getText().toString();
        String diagnosis = editDiagnosis.getText().toString();
        String treatment = editTreatment.getText().toString();

        presenter.pushMedicalRecordToServer(therapistName,patientName,dateCreated,problem,diagnosis,treatment, noteID, todolistModel.getId());

    }
}