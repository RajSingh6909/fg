package com.example.foodgospel.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodgospel.Activity.FarmerActivity;
import com.example.foodgospel.Activity.SectorActivity;
import com.example.foodgospel.Global.AddFarmerAllData;
import com.example.foodgospel.Models.Category;
import com.example.foodgospel.Models.Sector;
import com.example.foodgospel.Models.UserFarmer;
import com.example.foodgospel.R;

import java.util.ArrayList;

public class ViewDataListAdapter extends RecyclerView.Adapter<ViewDataListAdapter.ViewHolder> {

    Context context;
    ArrayList<Sector> arlAllSector = new ArrayList<>();

    public ViewDataListAdapter(Context context, ArrayList<Sector> arlAllSector) {
        this.context = context;
        this.arlAllSector = arlAllSector;
    }

    @NonNull
    @Override
    public ViewDataListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_all_raw_data, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override

    public void onBindViewHolder(@NonNull final ViewDataListAdapter.ViewHolder viewHolder, int i) {
        String strCategoryName = " ";

        Sector objSector = arlAllSector.get(i);
       // arlAllSector.get(i).setPosition(i);
        viewHolder.txtSectorName.setText(objSector.getName());
//        for (Category objCategory : objSector.arlCategoryList) {
//            strCategoryName += "," + objCategory.getName();
//            viewHolder.txtCategoryName.setText(strCategoryName);
//        }


    /*    viewHolder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                arlAllSector.get(viewHolder.getAdapterPosition());
                Sector objSector = arlAllSector.get(viewHolder.getAdapterPosition());
                Intent i = new Intent(context, SectorActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("SECTOREDIT", objSector);
                b.putBoolean("ISEDIT", true);
                i.putExtras(b);
                context.startActivity(i);

            }
        });*/


    }


    @Override
    public int getItemCount() {
        return arlAllSector.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtSectorName, txtCategoryName, txtCropName, txtHarvestMonth;
        ImageView imgEdit, imgDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtSectorName = itemView.findViewById(R.id.txtSelectedSectorName);
            txtCategoryName = itemView.findViewById(R.id.txtSelectedCategoryName);
            txtCropName = itemView.findViewById(R.id.txtCropName);
            txtHarvestMonth = itemView.findViewById(R.id.txtHarvestMonth);
            imgEdit = itemView.findViewById(R.id.imgEdit);
            imgDelete = itemView.findViewById(R.id.imgDelete);


        }
    }
}
