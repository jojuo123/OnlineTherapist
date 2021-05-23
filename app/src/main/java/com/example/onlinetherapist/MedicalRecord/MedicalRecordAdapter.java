package com.example.onlinetherapist.MedicalRecord;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinetherapist.MedicalRecord.patient.MedicalRecordMainActivity;
import com.example.onlinetherapist.MedicalRecord.patient.ViewMedicalRecordActivity;
import com.example.onlinetherapist.R;

import java.util.ArrayList;

public class MedicalRecordAdapter extends RecyclerView.Adapter<MedicalRecordAdapter.ViewHolder> {
    Context context;
    ArrayList<MedicalRecordModel> medicalRecordModels;
    int layout;

    public MedicalRecordAdapter(Context context, ArrayList<MedicalRecordModel> medicalRecordModels, int layout) {
        this.context = context;
        this.medicalRecordModels = medicalRecordModels;
        this.layout = layout;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layout,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvDiagnosis.setText("Diagnosis: "+medicalRecordModels.get(position).getDiagnosis());
        holder.tvProblems.setText("Problems: "+medicalRecordModels.get(position).getProblems());
        holder.tvDate.setText("Date: "+medicalRecordModels.get(position).getDate());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ViewMedicalRecordActivity.class);
                intent.putExtra("record",medicalRecordModels.get(position));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return medicalRecordModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDiagnosis, tvProblems, tvDate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tvDiagnosis = itemView.findViewById(R.id.tvDiagnosis);
            this.tvProblems = itemView.findViewById(R.id.tvProblem);
            this.tvDate = itemView.findViewById(R.id.tvDateCreated);
        }
    }
}
