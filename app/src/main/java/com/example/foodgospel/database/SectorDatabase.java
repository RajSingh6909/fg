package com.example.foodgospel.database;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;


import com.example.foodgospel.Dao.DaoAccess;
import com.example.foodgospel.Global.AddDairyAllData;
import com.example.foodgospel.Global.AddFarmerAllData;
import com.example.foodgospel.Global.AddPoultryFarmAllData;
import com.example.foodgospel.Global.Converters;
import com.example.foodgospel.Models.Category;
import com.example.foodgospel.Models.City;
import com.example.foodgospel.Models.Crop;
import com.example.foodgospel.Models.District;
import com.example.foodgospel.Models.Farmer;
import com.example.foodgospel.Models.LiveStockGrown;
import com.example.foodgospel.Models.MilkProduct;
import com.example.foodgospel.Models.Sector;
import com.example.foodgospel.Models.SectorDBModel;
import com.example.foodgospel.Models.State;


@Database(entities = {SectorDBModel.class, State.class, District.class, City.class, Category.class, Crop.class, Sector.class, MilkProduct.class, AddFarmerAllData.class, LiveStockGrown.class, AddDairyAllData.class, AddPoultryFarmAllData.class}, version = 12, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class SectorDatabase extends RoomDatabase {

    public abstract DaoAccess daoAccess();

    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @Override
    public void clearAllTables() {

    }

}
