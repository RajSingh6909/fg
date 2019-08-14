package com.example.foodgospel.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Dairy implements Serializable {

    @SerializedName("sector")
    @Expose
    private ArrayList<Sector> sector = null;

    public ArrayList<Sector> getSector() {
        return sector;
    }

    public void setSector(ArrayList<Sector> sector) {
        this.sector = sector;
    }

    @SerializedName("liveStockGrown")
    @Expose
    private ArrayList<LiveStockGrown> arlLiveStockGrown = null;

    public ArrayList<LiveStockGrown> getArlLiveStockGrown() {
        return arlLiveStockGrown;
    }

    public void setArlLiveStockGrown(ArrayList<LiveStockGrown> arlLiveStockGrown) {
        this.arlLiveStockGrown = arlLiveStockGrown;
    }
}