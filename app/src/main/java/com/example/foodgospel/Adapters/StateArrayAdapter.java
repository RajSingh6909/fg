package com.example.foodgospel.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.example.foodgospel.Models.Category;
import com.example.foodgospel.Models.Crop;
import com.example.foodgospel.Models.District;
import com.example.foodgospel.Models.Sector;
import com.example.foodgospel.Models.State;
import com.example.foodgospel.R;

import java.util.ArrayList;

public class StateArrayAdapter extends ArrayAdapter<State> {

    private LayoutInflater mInflater;
    private ArrayList<State> arlStateArrayAdapter;

    public StateArrayAdapter(@NonNull Context context, int resource, @NonNull ArrayList<State> dataArrayList) {
        super(context, resource, dataArrayList);
        mInflater = LayoutInflater.from(context);
        this.arlStateArrayAdapter = dataArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setText(arlStateArrayAdapter.get(position).getName());
        return label;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_spinner, parent, false);
        }

        TextView textView = convertView.findViewById(R.id.text);
        textView.setText(arlStateArrayAdapter.get(position).getName());
        textView.setTag(arlStateArrayAdapter.get(position).getId());
        return convertView;
    }

}

