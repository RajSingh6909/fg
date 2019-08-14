
package com.example.foodgospel.Models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Sector implements Serializable {

    @Ignore
    public Sector(int id, String sectorId, String name) {
        this.id = id;
        this.sectorId = sectorId;
        this.name = name;
    }

    public Sector() {

    }

    private int id;

    @NonNull
    @PrimaryKey
    @SerializedName("sector_id")
    @Expose
    private String sectorId;

    @SerializedName("name")
    @Expose
    private String name;

    //for getting data

    public String getSectorId() {
        return sectorId;
    }

    public void setSectorId(String sectorId) {
        this.sectorId = sectorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Ignore
    @SerializedName(value = "arlCategoryList" ,  alternate = {"category"})
    @Expose
    public ArrayList<Category> arlCategoryList = new ArrayList<>();

    public ArrayList<Category> getArlCategoryList() {
        return arlCategoryList;
    }

    public void setArlCategoryList(ArrayList<Category> arlCategoryList) {
        this.arlCategoryList = arlCategoryList;
    }


}
