package com.example.foodgospel.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodgospel.Adapters.ViewDairyAdapter;
import com.example.foodgospel.Adapters.ViewFarmerAdapter;
import com.example.foodgospel.Adapters.ViewPoultryFarmerAdapter;
import com.example.foodgospel.Global.ApiClient;
import com.example.foodgospel.Global.ConnectivityReceiver;
import com.example.foodgospel.Global.FoodGospelSharedPrefernce;
import com.example.foodgospel.Global.GlobalHandler;
import com.example.foodgospel.Models.AllFarmerData;
import com.example.foodgospel.Models.Farmer;
import com.example.foodgospel.Models.Language;
import com.example.foodgospel.Models.UserFarmer;
import com.example.foodgospel.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NavHomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView rcvFarmerDetails, rcvDairyDetails, rcvPoultryDetails;
    ArrayList<Farmer> arlFarmerList = new ArrayList<>();
    SharedPreferences mPrefs;
    TextView txtNoData, txtNoPoultryData, txtNoDairyData;
    ArrayList<UserFarmer> arlFarmerDataList = new ArrayList<>();
    TextView usertext;
    ArrayList<UserFarmer> arlFarmData = new ArrayList<>();
    ArrayList<UserFarmer> arlDairyDataList = new ArrayList<>();
    ArrayList<UserFarmer> arlPoultryDataList = new ArrayList<>();
    String strFarmerEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_home);


        mPrefs = getSharedPreferences("SectorPref", MODE_PRIVATE);

        rcvFarmerDetails = findViewById(R.id.rcvFarmerDetails);
        rcvDairyDetails = findViewById(R.id.rcvDairyDetails);
        rcvPoultryDetails = findViewById(R.id.rcvPoultryDetails);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        if (ConnectivityReceiver.checkconnection(getApplicationContext())) {
            getRegistredFarmer();
        } else {
            GlobalHandler.showconnectiondialog(getApplicationContext(), null);
        }

        //set farmer email from login to navigation drawer text
        SharedPreferences sharedPreferences = getSharedPreferences("loginPref", MODE_PRIVATE);
        strFarmerEmail = sharedPreferences.getString("value", "");
        View headerView = navigationView.getHeaderView(0);
        TextView navUseremail = (TextView) headerView.findViewById(R.id.nav_txtEmail);
        navUseremail.setText(strFarmerEmail);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.nav_home, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
////        if (id == R.id.action_settings) {
////            return true;
////        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_farmer) {
            Intent farmer = new Intent(NavHomeActivity.this, FarmerActivity.class);
            startActivity(farmer);

        } else if (id == R.id.nav_dairy) {
            Intent dairy = new Intent(NavHomeActivity.this, DairyActivity.class);
            startActivity(dairy);

        } else if (id == R.id.nav_poultry) {
            Intent poultry = new Intent(NavHomeActivity.this, PoultryFarmActivity.class);
            startActivity(poultry);

        } else if (id == R.id.nav_about) {

        } else if (id == R.id.nav_logout) {
            FoodGospelSharedPrefernce.setSharePrefernceData(this, "USERID", "");
            FoodGospelSharedPrefernce.setSharePrefernceData(this, "NAME", "");
            Intent i = new Intent(getApplicationContext(), LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //get registered farmer
    public void getRegistredFarmer() {
        Call<AllFarmerData> farmerCall = ApiClient.getPostService().getAllFarmerData(FoodGospelSharedPrefernce.getSharePrefernceData(this, "USERID"));
        farmerCall.enqueue(new Callback<AllFarmerData>() {
            @Override
            public void onResponse(Call<AllFarmerData> call, Response<AllFarmerData> response) {

                arlFarmerDataList.addAll(response.body().getUserFarmer());

                for (UserFarmer objUserFarmer : arlFarmerDataList) {
                    if (objUserFarmer.getFarmType().equalsIgnoreCase("1")) {
                        arlFarmData.add(objUserFarmer);
                    } else if (objUserFarmer.getFarmType().equalsIgnoreCase("2")) {
                        arlDairyDataList.add(objUserFarmer);
                    } else if (objUserFarmer.getFarmType().equalsIgnoreCase("3")) {
                        arlPoultryDataList.add(objUserFarmer);
                    }
                }

                String Message = response.body().getMessage();
                Toast.makeText(getApplicationContext(), Message, Toast.LENGTH_LONG).show();

                if (arlFarmData.size() == 0) {
                    txtNoData = findViewById(R.id.txtNoData);
                    txtNoData.setVisibility(View.VISIBLE);
                } else {
                    rcvFarmerDetails.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    rcvFarmerDetails.setAdapter(new ViewFarmerAdapter(NavHomeActivity.this, arlFarmData));
                }

                if (arlDairyDataList.size() == 0) {
                    txtNoDairyData = findViewById(R.id.txtNoDairyData);
                    txtNoDairyData.setVisibility(View.VISIBLE);
                } else {
                    rcvDairyDetails.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    rcvDairyDetails.setAdapter(new ViewDairyAdapter(NavHomeActivity.this, arlDairyDataList));
                }

                if (arlPoultryDataList.size() == 0) {
                    txtNoPoultryData = findViewById(R.id.txtNoPoultryData);
                    txtNoPoultryData.setVisibility(View.VISIBLE);
                } else {
                    rcvPoultryDetails.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    rcvPoultryDetails.setAdapter(new ViewPoultryFarmerAdapter(NavHomeActivity.this, arlPoultryDataList));
                }

            }

            @Override
            public void onFailure(Call<AllFarmerData> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "No Response", Toast.LENGTH_LONG).show();

                txtNoData = findViewById(R.id.txtNoData);
                txtNoData.setVisibility(View.VISIBLE);
            }
        });

    }
}
