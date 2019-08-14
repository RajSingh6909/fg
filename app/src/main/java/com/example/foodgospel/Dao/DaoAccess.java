package com.example.foodgospel.Dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;


import com.example.foodgospel.Global.AddDairyAllData;
import com.example.foodgospel.Global.AddFarmerAllData;
import com.example.foodgospel.Global.AddPoultryFarmAllData;
import com.example.foodgospel.Models.Category;
import com.example.foodgospel.Models.City;
import com.example.foodgospel.Models.Crop;
import com.example.foodgospel.Models.District;
import com.example.foodgospel.Models.LiveStockGrown;
import com.example.foodgospel.Models.MilkProduct;
import com.example.foodgospel.Models.Sector;
import com.example.foodgospel.Models.SectorDBModel;
import com.example.foodgospel.Models.State;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface DaoAccess {

    @Insert(onConflict = REPLACE)
    void insertTask(SectorDBModel sector);

    @Insert(onConflict = REPLACE)
    void insertState(State state);

    @Insert(onConflict = REPLACE)
    void insertDistrict(District district);

    @Insert(onConflict = REPLACE)
    void insertCity(City city);

    @Insert(onConflict = REPLACE)
    void insertSector(Sector sector);

    @Insert(onConflict = REPLACE)
    void insertCategory(Category category);

    @Insert(onConflict = REPLACE)
    void insertCrop(Crop crop);

    @Insert(onConflict = REPLACE)
    void insertMilkProduct(MilkProduct milkProduct);

    @Insert(onConflict = REPLACE)
    void insertFarmer(AddFarmerAllData addFarmerAllData);

    @Insert(onConflict = REPLACE)
    void insertDairy(AddDairyAllData objAddDairyAllData);

    @Insert(onConflict = REPLACE)
    void insertLiveStockGrown(LiveStockGrown objLiveStockGrown);

    @Insert(onConflict = REPLACE)
    void insertPoultryFarm(AddPoultryFarmAllData objAddPoultryFarmAllData);

//    @Insert
//    void insertRegisteredFarm(Farmer objFarmerData);

    @Delete
    void delete(State state);

    @Query("SELECT * FROM SectorDBModel")
    LiveData<List<SectorDBModel>> fetchAllTasks();

    @Query("SELECT * FROM State")
    LiveData<List<State>> fetchAllStateData();

    @Query("SELECT * FROM District WHERE state_id = :state_Id")
    LiveData<List<District>> fetchDistrictDataByStateId(int state_Id);

    @Query("SELECT * FROM City WHERE state_id = :state_id")
    LiveData<List<City>> fetchAllCityData(int state_id);

    @Query("SELECT * FROM Sector")
    LiveData<List<Sector>> fetchAllSectorData();

    @Query("SELECT * FROM Category WHERE sectorId = :sectorId")
    LiveData<List<Category>> fetchSectorWiseCategory(int sectorId);

    @Query("SELECT * FROM Crop WHERE categoryId = :categoryId")
    LiveData<List<Crop>> fetchCategoryWiseCrop(int categoryId);

    @Query("SELECT * FROM MilkProduct")
    LiveData<List<MilkProduct>> fetchAllProductData();

    @Query("SELECT * FROM MilkProduct where producetype = :producetype")
    LiveData<List<MilkProduct>> fetchAllProductDataWithId(int producetype);

    @Query("SELECT * FROM LiveStockGrown")
    LiveData<List<LiveStockGrown>> fetchAllLiveStockGrownData();

}
