package com.example.foodgospel.Adapters;

import android.app.Activity;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodgospel.Models.Category;
import com.example.foodgospel.Models.Crop;
import com.example.foodgospel.R;
import com.example.foodgospel.Repository.SectorRepository;

import java.util.ArrayList;
import java.util.List;


public class HarvestListAdapter extends RecyclerView.Adapter<HarvestListAdapter.HarvestViewHolder> {

    Context context;
    ArrayList<String> arlCropNameList = new ArrayList<>();
    Integer cropId;
    String cropName;



    public HarvestListAdapter(Context context,ArrayList<String> arlCropNameList) {
        this.context = context;
        this.arlCropNameList = arlCropNameList;

    }


    @NonNull
    @Override
    public HarvestViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_harvest_cell, viewGroup, false);
        return new HarvestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HarvestViewHolder viewHolder, int i) {
        viewHolder.txtSelectedCropName.setText(arlCropNameList.get(i));
    }


    @Override
    public int getItemCount() {
        return arlCropNameList.size();
    }

    public class HarvestViewHolder extends RecyclerView.ViewHolder {

        TextView txtSelectedCropName;
        Spinner spharvestName;


        public HarvestViewHolder(@NonNull View itemView) {
            super(itemView);
            txtSelectedCropName = itemView.findViewById(R.id.txtSelectedCropName);
            spharvestName = itemView.findViewById(R.id.spharvestName);

        }
    }


}
