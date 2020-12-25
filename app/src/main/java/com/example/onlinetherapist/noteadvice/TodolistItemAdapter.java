package com.example.onlinetherapist.noteadvice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.onlinetherapist.R;
import com.example.onlinetherapist.noteadvice.patient.IViewATodolistPresenter;

import java.util.List;

public class TodolistItemAdapter extends ArrayAdapter<TodolistItemModel> {

    int mResource;
    List<TodolistItemModel> mData;
    IViewATodolistPresenter presenter;

    public TodolistItemAdapter(@NonNull Context context, int resource, @NonNull List<TodolistItemModel> objects) {
        super(context, resource, objects);
        mResource=resource;
        mData=objects;
        presenter = null;
    }

    public void setPresenter (IViewATodolistPresenter presenter) {this.presenter=presenter;}

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView==null) {
            convertView = LayoutInflater.from(getContext()).inflate(mResource, null, false);
        }
        TodolistItemModel model = mData.get(position);
        CheckBox checkBox=convertView.findViewById(R.id.todolistitemContent);
        checkBox.setText(model.getContent());
        int status=model.getStatus();
        checkBox.setChecked(status != 0);

        if (presenter != null) {
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    presenter.userInteractWithModel(model, b);
                }
            });
        }
        return convertView;
    }

    @Nullable
    @Override
    public TodolistItemModel getItem(int position) {
        return mData.get(position);
    }


    public void updateDataset(List<TodolistItemModel> newDataset) {
        //mData = newDataset;
        mData.clear();
        mData.addAll(newDataset);
        notifyDataSetChanged();
    }
}
