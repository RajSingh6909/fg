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
public class LiveStockGrown implements Serializable {

    @NonNull
    @PrimaryKey
    private int id;

    @SerializedName("name")
    @Expose
    private String produceName;

    @SerializedName(value = "livestock", alternate = {"liveStock"})
    private String liveStock;

    private String Others;

    @Ignore
    @SerializedName(value="arlMilkProduct", alternate={"milkProduct"})
    private ArrayList<MilkProduct> arlMilkProduct = new ArrayList<>();

    public LiveStockGrown() {
    }

    @Ignore
    public LiveStockGrown(int id, String produceName) {
        this.id = id;
        this.produceName = produceName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProduceName() {
        return produceName;
    }

    public void setProduceName(String produceName) {
        this.produceName = produceName;
    }

    public ArrayList<MilkProduct> getArlMilkProduct() {
        return arlMilkProduct;
    }

    public void setArlMilkProduct(ArrayList<MilkProduct> arlMilkProduct) {
        this.arlMilkProduct = arlMilkProduct;
    }

    public String getLiveStock() {
        return liveStock;
    }

    public void setLiveStock(String liveStock) {
        this.liveStock = liveStock;
    }

    public String getOthers() {
        return Others;
    }

    public void setOthers(String others) {
        Others = others;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LiveStockGrown that = (LiveStockGrown) o;
        return Objects.equals(id, that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
