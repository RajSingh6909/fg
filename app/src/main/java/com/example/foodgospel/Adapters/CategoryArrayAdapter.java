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
import com.example.foodgospel.Models.Sector;
import com.example.foodgospel.R;

import java.util.ArrayList;

public class CategoryArrayAdapter extends ArrayAdapter<Category> {

    private LayoutInflater mInflater;
    private ArrayList<Category> cropSpinnerData;

    public CategoryArrayAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Category> dataArrayList) {
        super(context, resource, dataArrayList);
        mInflater = LayoutInflater.from(context);
        this.cropSpinnerData = dataArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setText(cropSpinnerData.get(position).getCategoryName());
        return label;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_spinner, parent, false);
        }

        TextView textView = convertView.findViewById(R.id.text);
        textView.setText(cropSpinnerData.get(position).getCategoryName());
        textView.setTag(cropSpinnerData.get(position).getCategoryId());
        return convertView;
    }


}

