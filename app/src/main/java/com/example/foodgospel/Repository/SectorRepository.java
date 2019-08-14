package com.example.foodgospel.Repository;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;

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
import com.example.foodgospel.database.SectorDatabase;

import java.util.ArrayList;
import java.util.List;

public class SectorRepository {

    //repository for perform list of operations likes insert,update,delete,get.
    private String DB_NAME = "db_foodgospel";
    private SectorDatabase sectorDatabase;

    public SectorRepository(Context context) {
        sectorDatabase = Room.databaseBuilder(context, SectorDatabase.class, DB_NAME)
                .fallbackToDestructiveMigration().allowMainThreadQueries().build();
    }


    public void insertSectorTask(final SectorDBModel sector) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                sectorDatabase.daoAccess().insertTask(sector);
                return null;
            }
        }.execute();
    }


    //For state insertion
    public void insertStateList(int id,String name) {
        insertStatename(id,name);
    }

    public void insertStatename(int id,String name) {
        State objState = new State();
        objState.setId(id);
        objState.setName(name);
        insertState(objState);
    }

    public void insertState(final State state) {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                sectorDatabase.daoAccess().insertState(state);
                return null;
            }
        }.execute();
    }

    //For district insertion
    public void insertDistrictNameList(int id,int state_id,String name) {
        insertDistrict(id,state_id,name);
    }

    public void insertDistrict(int id,int State_id,String name) {
        District objDistrict = new District();
        objDistrict.setId(id);
        objDistrict.setState_id(State_id);
        objDistrict.setName(name);
        insertDistrict(objDistrict);
    }

    public void insertDistrict(final District district) {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                sectorDatabase.daoAccess().insertDistrict(district);
                return null;
            }
        }.execute();
    }

    public void deleteTask(final State state) {
        deleteState(state);
    }

    //For district insertion
    public void insertCityList(int id,int state_id,String name) {
        insertCityName(id,state_id,name);
    }

    public void insertCityName(int id,int state_id,String name) {
        City objCity = new City();
        objCity.setName(name);
        objCity.setState_id(state_id);
        objCity.setId(id);
        insertCity(objCity);
    }

    public void insertCity(final City city) {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                sectorDatabase.daoAccess().insertCity(city);
                return null;
            }
        }.execute();
    }

    public void insertSectorName(String sector_id, String name) {
        insertSectorNameData(sector_id, name);
    }

    public void insertSectorNameData(String sector_id, String name) {
        Sector objSector = new Sector();
        objSector.setSectorId(sector_id);
        objSector.setName(name);
        insertSector(objSector);
    }

    public void insertSector(final Sector sector) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                sectorDatabase.daoAccess().insertSector(sector);
                return null;
            }
        }.execute();
    }

    public void insertCategoryName(String category_id, String sector_id, String category_name) {
        insertCategoryNameData(category_id, sector_id, category_name);
    }

    public void insertCategoryNameData(String category_id, String sector_id, String category_name) {
        Category objCategory = new Category();
        objCategory.setCategoryId(category_id);
        objCategory.setSectorId(sector_id);
        objCategory.setCategoryName(category_name);
        insertCategory(objCategory);
    }

    public void insertCategory(final Category category) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                sectorDatabase.daoAccess().insertCategory(category);
                return null;
            }
        }.execute();
    }

    public void insertCropName(String crop_id, String category_id, String name, int is_produce) {
        insertCropNameData(crop_id, category_id, name, is_produce);
    }

    public void insertCropNameData(String crop_id, String category_id, String name, int is_produce) {
        Crop objCrop = new Crop();
        objCrop.setCropId(crop_id);
        objCrop.setCategoryId(category_id);
        objCrop.setCropName(name);
        objCrop.setIs_produce(is_produce);
        insertCrop(objCrop);
    }


    public void insertCrop(final Crop crop) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                sectorDatabase.daoAccess().insertCrop(crop);
                return null;
            }
        }.execute();
    }


    public void insertMilkProductData(int product_id, int produce_type, String ProductName) {
        MilkProduct objMilkProduct = new MilkProduct();
        objMilkProduct.setProductId(String.valueOf(product_id));
        objMilkProduct.setProduceType(String.valueOf(produce_type));
        objMilkProduct.setName(ProductName);
        insertMilkProduct(objMilkProduct);
    }

    public void insertMilkProduct(final MilkProduct objMilkproduct) {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                sectorDatabase.daoAccess().insertMilkProduct(objMilkproduct);
                return null;
            }
        }.execute();
    }


    public void insertFarmer(final AddFarmerAllData objAddFarmerAllData) {

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                sectorDatabase.daoAccess().insertFarmer(objAddFarmerAllData);
                return null;
            }
        }.execute();
    }

    public void insertDairyData(final AddDairyAllData objAddDairyData)
    {
        new AsyncTask<Void,Void,Void>(){

            @Override
            protected Void doInBackground(Void... voids) {
                sectorDatabase.daoAccess().insertDairy(objAddDairyData);
                return null;
            }
        }.execute();
    }

    public void insertPoultryData(final AddPoultryFarmAllData objAddPoultryFarmAllData)
    {
        new AsyncTask<Void,Void,Void>(){

            @Override
            protected Void doInBackground(Void... voids) {
                sectorDatabase.daoAccess().insertPoultryFarm(objAddPoultryFarmAllData);
                return null;
            }
        }.execute();
    }

    public void insertLiveStockGrownData(int stockId, String stokeName) {
        LiveStockGrown objLiveStockGrown = new LiveStockGrown();
        objLiveStockGrown.setId(stockId);
        objLiveStockGrown.setProduceName(stokeName);
        insertLiveStockGrown(objLiveStockGrown);
    }

    public void insertLiveStockGrown(final LiveStockGrown objLiveStockGrown) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                sectorDatabase.daoAccess().insertLiveStockGrown(objLiveStockGrown);
                return null;
            }
        }.execute();
    }

//    public void insertRegisteredFarmer(final Farmer objFarmer) {
//        new AsyncTask<Void, Void, Void>() {
//
//            @Override
//            protected Void doInBackground(Void... voids) {
//                sectorDatabase.daoAccess().insertRegisteredFarm(objFarmer);
//                return null;
//            }
//        }.execute();
//    }


    public void deleteState(final State state) {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                sectorDatabase.daoAccess().delete(state);
                return null;
            }
        }.execute();
    }

    //For display Sector
    public LiveData<List<SectorDBModel>> getTasks() {
        return sectorDatabase.daoAccess().fetchAllTasks();
    }

    //For display state
    public LiveData<List<State>> getAllState() {
        return sectorDatabase.daoAccess().fetchAllStateData();
    }

    //For display district
    public LiveData<List<District>> getAllDistrict(int state_id) {
        return sectorDatabase.daoAccess().fetchDistrictDataByStateId(state_id);
    }

    //For display city
    public LiveData<List<City>> getAllCityData(int state_id) {
        return sectorDatabase.daoAccess().fetchAllCityData(state_id);
    }

    //For sector data
    public LiveData<List<Sector>> getAllSector() {
        return sectorDatabase.daoAccess().fetchAllSectorData();
    }

    //For sector wise category
    public LiveData<List<Category>> getAllSectorWiseCategory(int sector_id) {
        return sectorDatabase.daoAccess().fetchSectorWiseCategory(sector_id);
    }

    //For category wise crop
    public LiveData<List<Crop>> getAllCategoryWiseCropData(int category_id) {
        return sectorDatabase.daoAccess().fetchCategoryWiseCrop(category_id);
    }

    //For All product
    public LiveData<List<MilkProduct>> getAllProductData() {
        return sectorDatabase.daoAccess().fetchAllProductData();
    }

    //for product data from producetype
    public LiveData<List<MilkProduct>> getAllProductDataProduceType(int producetype) {
        return sectorDatabase.daoAccess().fetchAllProductDataWithId(producetype);
    }

    //For LiveStockGrown data
    public LiveData<List<LiveStockGrown>> getAllLiveStockGrownData() {
        return sectorDatabase.daoAccess().fetchAllLiveStockGrownData();
    }
}
