package com.example.foodgospel.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodgospel.Activity.DairyActivity;
import com.example.foodgospel.Activity.FarmerActivity;
import com.example.foodgospel.Activity.PoultryFarmActivity;
import com.example.foodgospel.Global.ApiClient;
import com.example.foodgospel.Models.Farm;
import com.example.foodgospel.Models.Farmer;
import com.example.foodgospel.Models.PostResponse;
import com.example.foodgospel.Models.UserFarmer;
import com.example.foodgospel.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewDairyAdapter extends RecyclerView.Adapter<ViewDairyAdapter.ViewDairyViewHolder> {

    Context context;
    ArrayList<UserFarmer> arlFarmer = new ArrayList<>();

    public ViewDairyAdapter(Context context, ArrayList<UserFarmer> arlFarmer) {
        this.context = context;
        this.arlFarmer = arlFarmer;

    }

    @NonNull
    @Override
    public ViewDairyAdapter.ViewDairyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.raw_dairy_farmer, viewGroup, false);
        return new ViewDairyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewDairyAdapter.ViewDairyViewHolder farmerViewHolder, int i) {

        final UserFarmer objFarmer = arlFarmer.get(i);

        if(objFarmer.getFarmType().equalsIgnoreCase("2")) {

            farmerViewHolder.txtFarmerName.setText(objFarmer.getName());
            farmerViewHolder.txtComapnyName.setText(objFarmer.getCompanyName());
            farmerViewHolder.txtEmail.setText(objFarmer.getEmail());
            farmerViewHolder.txtMobile.setText(objFarmer.getMobile());

            farmerViewHolder.imgEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    arlFarmer.get(farmerViewHolder.getAdapterPosition());
                    UserFarmer objUserFarmer = arlFarmer.get(farmerViewHolder.getAdapterPosition());
                    Intent i = new Intent(context, DairyActivity.class);
                    Bundle b = new Bundle();
                    b.putSerializable("DairyEditData", objUserFarmer);
                    b.putBoolean("isEdit", true);
                    i.putExtras(b);
                    context.startActivity(i);
                }
            });

            farmerViewHolder.imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    arlFarmer.get(farmerViewHolder.getAdapterPosition());
                    UserFarmer objUserFarmer = arlFarmer.get(farmerViewHolder.getAdapterPosition());
                    final int farmer_id = Integer.parseInt(objUserFarmer.getFarmerId());

                    //Alert Dialog for delete farmer
                    final Dialog dialog = new Dialog(v.getContext());
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.alert_delete_farmer);
                    dialog.findViewById(R.id.btnYes).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            deleteFarmer(farmer_id);
                            arlFarmer.remove(arlFarmer.get(farmerViewHolder.getAdapterPosition()));
                            notifyDataSetChanged();
                        }
                    });

                    dialog.findViewById(R.id.btnNo).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    dialog.show();
                }

            });
        }
    }

    @Override
    public int getItemCount() {
        return arlFarmer.size();
    }

    public class ViewDairyViewHolder extends RecyclerView.ViewHolder {

        TextView txtFarmerName, txtMobile, txtEmail, txtCity, txtComapnyName;
        ImageView imgEdit, imgDelete;

        public ViewDairyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtFarmerName = itemView.findViewById(R.id.txtFarmerName);
            txtMobile = itemView.findViewById(R.id.txtMobile);
            txtEmail = itemView.findViewById(R.id.txtEmail);
            txtCity = itemView.findViewById(R.id.txtCity);
            txtComapnyName = itemView.findViewById(R.id.txtCompanyName);

            imgEdit = itemView.findViewById(R.id.imgEdit);
            imgDelete = itemView.findViewById(R.id.imgDelete);

        }
    }

    public void deleteFarmer(int Farmer_id) {
        Call<PostResponse> farmerCall = ApiClient.getPostService().deleteFarmer(Farmer_id);
        farmerCall.enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                String Message = response.body().getMessage();
                Toast.makeText(context, Message, Toast.LENGTH_LONG).show();
                notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {
                Toast.makeText(context, "No Response from Server!", Toast.LENGTH_LONG).show();

            }
        });

    }
}
