package com.example.onlinetherapist.noteadvice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.onlinetherapist.R;

import java.util.List;

public class TodolistAdapter extends ArrayAdapter<TodolistModel> {

    int mResource;
    List<TodolistModel> mData;

    public TodolistAdapter(@NonNull Context context, int resource, @NonNull List<TodolistModel> objects) {
        super(context, resource, objects);
        mResource=resource;
        mData=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView==null) {
            convertView = LayoutInflater.from(getContext()).inflate(mResource, null, false);
        }
        TodolistModel todolistModel = mData.get(position);
        TextView todolistDate=convertView.findViewById(R.id.todolistDate);
        todolistDate.setText(todolistModel.getCreated_dateString());
        return convertView;
    }

    public void updateDataset(List<TodolistModel> newTodolistList) {
        //mData = newTodolistList; //wont display
        mData.clear();
        mData.addAll(newTodolistList);
        notifyDataSetChanged();
    }

    @Nullable
    @Override
    public TodolistModel getItem(int position) {
        return mData.get(position);
    }
}
