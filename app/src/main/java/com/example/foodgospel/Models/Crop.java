
package com.example.foodgospel.Models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

@Entity
public class Crop implements Serializable {


    private int id;

    @NonNull
    @PrimaryKey
    @SerializedName("crop_id")
    @Expose
    private String cropId;
    @SerializedName("category_id")
    @Expose
    private String categoryId;

    public Crop() {
    }

    @Ignore
    public Crop(int id, String cropId, String categoryId, String name) {
        this.id = id;
        this.cropId = cropId;
        this.categoryId = categoryId;
        this.cropName = name;
    }

//    @SerializedName("name")
//    @Expose
//    private String name;



    @SerializedName("is_produce")
    @Expose
    private int is_produce;

    @SerializedName(value = "livestock",alternate = {"liveStock"})
    @Expose
    private String liveStock = " ";

    @SerializedName(value = "NoOfPoultry",alternate = {"no_of_poultry"})
    @Expose
    private int no_of_poultry = 0;

    //For retriving data

    @SerializedName("sector_id")
    @Expose
    private String sectorId;
    @SerializedName("sector_name")
    @Expose
    private String sectorName;

    @SerializedName("category_name")
    @Expose
    private String categoryName;

    @SerializedName(value="name", alternate={"crop_name"})
    @Expose
    private String cropName;

    @SerializedName(value = "month",alternate = {"monthName"})
    @Expose
    private ArrayList<String> month = null;

    public ArrayList<String> getMonth() {
        return month;
    }

    public void setMonth(ArrayList<String> month) {
        this.month = month;
    }

    public String getCropId() {
        return cropId;
    }

    public void setCropId(String cropId) {
        this.cropId = cropId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIs_produce() {
        return is_produce;
    }

    public void setIs_produce(int is_produce) {
        this.is_produce = is_produce;
    }

    public String getLiveStock() {
        return liveStock;
    }

    public void setLiveStock(String liveStock) {
        this.liveStock = liveStock;
    }

    @Ignore
    public ArrayList<HarvestMonth> arlHarvestmonth = new ArrayList<>();

    @Ignore
    public ArrayList<MilkProduct> arlMilkProduct = new ArrayList<>();

    public ArrayList<HarvestMonth> getArlHarvestmonth() {
        return arlHarvestmonth;
    }

    public void setArlHarvestmonth(ArrayList<HarvestMonth> arlHarvestmonth) {
        this.arlHarvestmonth = arlHarvestmonth;
    }

    public ArrayList<MilkProduct> getArlMilkProduct() {
        return arlMilkProduct;
    }

    public void setArlMilkProduct(ArrayList<MilkProduct> arlMilkProduct) {
        this.arlMilkProduct = arlMilkProduct;
    }

    public int getNo_of_poultry() {
        return no_of_poultry;
    }

    public void setNo_of_poultry(int no_of_poultry) {
        this.no_of_poultry = no_of_poultry;
    }

    public String getSectorId() {
        return sectorId;
    }

    public void setSectorId(String sectorId) {
        this.sectorId = sectorId;
    }

    public String getSectorName() {
        return sectorName;
    }

    public void setSectorName(String sectorName) {
        this.sectorName = sectorName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Crop that = (Crop) o;
        return Objects.equals(cropId.toLowerCase(), that.getCropId().toLowerCase());
    }

    @Override
    public int hashCode() {
        return Objects.hash(cropId);
    }
}
