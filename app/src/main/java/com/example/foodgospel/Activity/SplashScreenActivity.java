package com.example.foodgospel.Activity;


import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;

import android.preference.PreferenceManager;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.foodgospel.Global.ApiClient;
import com.example.foodgospel.Global.Config;
import com.example.foodgospel.Global.ConnectivityReceiver;
import com.example.foodgospel.Global.FoodGospelSharedPrefernce;
import com.example.foodgospel.Global.GlobalHandler;
import com.example.foodgospel.Global.Utility;
import com.example.foodgospel.Models.AllData;
import com.example.foodgospel.Models.AllFarmerData;
import com.example.foodgospel.Models.AllMasterData;
import com.example.foodgospel.Models.Category;
import com.example.foodgospel.Models.City;
import com.example.foodgospel.Models.Crop;
import com.example.foodgospel.Models.District;

import com.example.foodgospel.Models.Farmer;
import com.example.foodgospel.Models.GetAllRegionData;
import com.example.foodgospel.Models.GetRegisteredFarmerData;
import com.example.foodgospel.Models.LiveStockGrown;
import com.example.foodgospel.Models.MilkProduct;
import com.example.foodgospel.Models.Sector;
import com.example.foodgospel.Models.SectorDBModel;
import com.example.foodgospel.Models.SectorDetailsModel;
import com.example.foodgospel.Models.State;
import com.example.foodgospel.Models.UserFarmer;
import com.example.foodgospel.R;
import com.example.foodgospel.Repository.SectorRepository;
import com.google.gson.Gson;
import com.google.gson.JsonObject;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreenActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;
    Boolean isFirstTime;

    private ArrayList<District> arlDistrict = new ArrayList<>();

    //String List for data passing
    private ArrayList<Farmer> arlDairyFarmerDataList = new ArrayList<>();

    private ArrayList<SectorDetailsModel> arlSectorName = new ArrayList<>();
    private ArrayList<State> arlStatelList = new ArrayList<>();
    private ArrayList<City> arlCityList = new ArrayList<>();
    private ArrayList<Sector> arlSectorList = new ArrayList<>();
    private ArrayList<Category> arlCategoryList = new ArrayList<>();
    private ArrayList<Crop> arlCropList = new ArrayList<>();
    private ArrayList<MilkProduct> arlMilkProductList = new ArrayList<>();
    private ArrayList<LiveStockGrown> arlLiveStockGrown = new ArrayList<>();


    private String name, id, sectorname, json, sectorId, stateName, stateId, stateNameList, districtName, districtNameList, districtId, cityId, cityName, cityNameList, categoryId, categoryName, categoryNameList;
    Context context;

    State objState;
    District objDistrict;
    City objCity;

    SectorDetailsModel objSectorDetailsModel;
    SharedPreferences mPrefs;
    SharedPreferences.Editor prefsEditor;
    Integer liveInsertedId, localInsertedId, statePrefId, state_Id, cityStateId, districtstateId;

    private SectorRepository objSectorRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        context = getApplicationContext();

        objSectorRepository = new SectorRepository(getApplicationContext());

        // objDatabaseHelper = new DatabaseHelper(this);

        //set preference
        mPrefs = getSharedPreferences("SectorPref", MODE_PRIVATE);

        //splashscreen
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                SharedPreferences app_preferences = PreferenceManager
                        .getDefaultSharedPreferences(SplashScreenActivity.this);

                SharedPreferences.Editor editor = app_preferences.edit();

                isFirstTime = app_preferences.getBoolean("isFirstTime", true);

                //  if (isFirstTime)
                if (Utility.isNotLogin(SplashScreenActivity.this)) {
                    //implement your first time logic
                    Intent i = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    startActivity(i);
                 /*   editor.putBoolean("isFirstTime", false);
                    editor.commit();*/

                } else {
                    //app open directly
                    Intent i = new Intent(SplashScreenActivity.this, NavHomeActivity.class);

                    startActivity(i);
                }

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);

        if (ConnectivityReceiver.checkconnection(getApplicationContext())) {
            getAllRegionData();
            getAllMasterData();
        } else {
            GlobalHandler.showconnectiondialog(getApplicationContext(), null);
        }


    }

    //for all Master data
    public void getAllMasterData() {

        Call<AllMasterData> cropCall = ApiClient.getPostService().getAllMasterData();
        cropCall.enqueue(new Callback<AllMasterData>() {
            @Override
            public void onResponse(Call<AllMasterData> call, Response<AllMasterData> response) {
                AllMasterData objAllMasterData = response.body();
                arlSectorList.addAll(objAllMasterData.getSector());
                arlCategoryList.addAll(objAllMasterData.getCategory());
                arlCropList.addAll(objAllMasterData.getCrop());
                arlMilkProductList.addAll(objAllMasterData.getMilkProduct());
                arlLiveStockGrown.addAll(objAllMasterData.getLiveStockGrowns());

                for (int i = 0; i < arlSectorList.size(); i++) {
                    String Id = arlSectorList.get(i).getSectorId();
                    String Name = arlSectorList.get(i).getName();
                    objSectorRepository.insertSectorNameData(Id, Name);

                }

                //for category List
                for (int i = 0; i < arlCategoryList.size(); i++) {
                    String category_id = arlCategoryList.get(i).getCategoryId();
                    String sector_id = arlCategoryList.get(i).getSectorId();
                    String name = arlCategoryList.get(i).getCategoryName();
                    objSectorRepository.insertCategoryNameData(category_id, sector_id, name);
                }

                //for crop List

                for (int i = 0; i < arlCropList.size(); i++) {
                    String crop_id = arlCropList.get(i).getCropId();
                    String category_id = arlCropList.get(i).getCategoryId();
                    String name = arlCropList.get(i).getCropName();
                    int is_produce = arlCropList.get(i).getIs_produce();
                    objSectorRepository.insertCropNameData(crop_id, category_id, name, is_produce);
                }

                //for Milk Product

                for (int i = 0; i < arlMilkProductList.size(); i++) {
                    int ProductType = Integer.parseInt(arlMilkProductList.get(i).getProduceType());
                    String ProductName = arlMilkProductList.get(i).getName();
                    int ProductId = Integer.parseInt(arlMilkProductList.get(i).getProductId());
                    objSectorRepository.insertMilkProductData(ProductId, ProductType, ProductName);
                }

                //for LiveStock Grown

                for (int i = 0; i < arlLiveStockGrown.size(); i++) {
                    int stockId = arlLiveStockGrown.get(i).getId();
                    String stockName = arlLiveStockGrown.get(i).getProduceName();
                    objSectorRepository.insertLiveStockGrownData(stockId, stockName);
                }
            }

            @Override
            public void onFailure(Call<AllMasterData> call, Throwable t) {

            }
        });

    }

    //for all region data
    public void getAllRegionData() {

        Call<GetAllRegionData> cropCall = ApiClient.getPostService().getAllRegionData();
        cropCall.enqueue(new Callback<GetAllRegionData>() {
            @Override
            public void onResponse(Call<GetAllRegionData> call, Response<GetAllRegionData> response) {
                AllData objAllData = response.body().getAllData();

                arlStatelList.addAll(objAllData.getState());
                arlDistrict.addAll(objAllData.getDistrict());
                arlCityList.addAll(objAllData.getCity());


                for (int i = 0; i < arlStatelList.size(); i++) {
                    int Id = arlStatelList.get(i).getId();
                    String Name = arlStatelList.get(i).getName();
                    objSectorRepository.insertStateList(Id, Name);

                }

                for (int i = 0; i < arlDistrict.size(); i++) {
                    int id = arlDistrict.get(i).getId();
                    int state_id = arlDistrict.get(i).getState_id();
                    String name = arlDistrict.get(i).getName();
                    objSectorRepository.insertDistrictNameList(id, state_id, name);

                }

                //for crop List

                for (int i = 0; i < arlCityList.size(); i++) {
                    int id = arlCityList.get(i).getId();
                    String name = arlCityList.get(i).getName();
                    int state_id = arlCityList.get(i).getState_id();
                    objSectorRepository.insertCityList(id,state_id,name);
                }
            }

            @Override
            public void onFailure(Call<GetAllRegionData> call, Throwable t) {

            }
        });

    }



}
