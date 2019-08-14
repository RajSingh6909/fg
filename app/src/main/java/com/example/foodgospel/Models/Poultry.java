package com.example.foodgospel.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Poultry implements Serializable {

    @SerializedName("sector")
    @Expose
    private List<Sector> sector = null;

    public List<Sector> getSector() {
        return sector;
    }

    public void setSector(List<Sector> sector) {
        this.sector = sector;
    }
}