package com.example.foodgospel.Adapters;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.example.foodgospel.Models.Crop;
import com.example.foodgospel.Models.HarvestMonth;
import com.example.foodgospel.Models.Language;
import com.example.foodgospel.R;

import java.util.ArrayList;

public class LanguageArrayAdapter extends ArrayAdapter<Language> {

    private LayoutInflater mInflater;
    private ArrayList<Language> arlLanguageList;

    public LanguageArrayAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Language> dataArrayList) {
        super(context, resource, dataArrayList);
        mInflater = LayoutInflater.from(context);
        this.arlLanguageList = dataArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setText(arlLanguageList.get(position).getName());
        return label;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_spinner, parent, false);
        }

        TextView textView = convertView.findViewById(R.id.text);
        textView.setText(arlLanguageList.get(position).getName());
        textView.setTag(arlLanguageList.get(position).getId());
        return convertView;
    }


}
