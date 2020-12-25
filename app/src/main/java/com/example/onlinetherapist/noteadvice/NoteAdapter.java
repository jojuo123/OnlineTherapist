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

public class NoteAdapter extends ArrayAdapter<NoteModel> {

    List<NoteModel> mData;
    int mResource;
    public NoteAdapter(@NonNull Context context, int resource, @NonNull List<NoteModel> objects) {
        super(context, resource, objects);
        mData=objects;
        mResource=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
             convertView = LayoutInflater.from(getContext()).inflate(mResource, null, false);
             NoteModel noteInfo = mData.get(position);
             TextView noteDate=convertView.findViewById(R.id.noteDate);
             TextView noteContent=convertView.findViewById(R.id.noteContent);
             noteDate.setText(noteInfo.getDateString());
             noteContent.setText(noteInfo.getContent());
        }
        return convertView;
        //return super.getView(position, convertView, parent);
    }

    public void updateDataset (List<NoteModel> newDataset) {
        //mData=newDataset;
        mData.clear();
        mData.addAll(newDataset);
        notifyDataSetChanged();

    }

    @Nullable
    @Override
    public NoteModel getItem(int position) {
        return mData.get(position);
    }
}
