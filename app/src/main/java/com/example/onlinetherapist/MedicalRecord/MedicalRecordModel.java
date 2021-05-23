package com.example.onlinetherapist.MedicalRecord;

import java.io.Serializable;

public class MedicalRecordModel implements Serializable {
    private String id;
    private String patient;
    private String therapist;
    private String date;
    private String problems;
    private String diagnosis;
    private String treatment;
    private String noteID;
    private String todoListID;

    public MedicalRecordModel(String id, String patient, String therapist, String date, String problems, String diagnosis, String treatment, String noteID, String todoListID) {
        this.id = id;
        this.patient = patient;
        this.therapist = therapist;
        this.date = date;
        this.problems = problems;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
        this.noteID = noteID;
        this.todoListID = todoListID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    public String getTherapist() {
        return therapist;
    }

    public void setTherapist(String therapist) {
        this.therapist = therapist;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getProblems() {
        return problems;
    }

    public void setProblems(String problems) {
        this.problems = problems;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public String getNoteID() {
        return noteID;
    }

    public void setNoteID(String noteID) {
        this.noteID = noteID;
    }

    public String getTodoListID() {
        return todoListID;
    }

    public void setTodoListID(String todoListID) {
        this.todoListID = todoListID;
    }
}
