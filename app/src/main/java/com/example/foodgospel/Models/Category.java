
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
import java.util.Objects;

@Entity
public class Category implements Serializable {

    private int id;

    @NonNull
    @PrimaryKey
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("sector_id")
    @Expose
    private String sectorId;
//    @SerializedName("name")
//    @Expose
//    private String name;

    //for getting response from getUserData for edit


    @SerializedName(value="name", alternate={"category_name"})
    @Expose
    private String categoryName;


    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryId() {
        return categoryId;
    }
    public  Category(){}

    @Ignore
    public Category(int id, String categoryId, String sectorId, String name) {
        this.id = id;
        this.categoryId = categoryId;
        this.sectorId = sectorId;
        this.categoryName = name;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getSectorId() {
        return sectorId;
    }

    public void setSectorId(String sectorId) {
        this.sectorId = sectorId;
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

    @Ignore
    @Expose
    @SerializedName(value = "arlCrop" ,  alternate = {"crop"})
    public ArrayList<Crop> arlCrop = new ArrayList<>();

    public ArrayList<Crop> getArlCrop() {
        return arlCrop;
    }

    public void setArlCrop(ArrayList<Crop> arlCrop) {
        this.arlCrop = arlCrop;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category that = (Category) o;
        return Objects.equals(categoryId, that.getCategoryId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(sectorId);
    }
}
