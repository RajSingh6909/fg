
package com.example.foodgospel.Models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllData {

    @SerializedName("city")
    @Expose
    private List<City> city = null;
    @SerializedName("state")
    @Expose
    private List<State> state = null;
    @SerializedName("district")
    @Expose
    private List<District> district = null;

    public List<City> getCity() {
        return city;
    }

    public void setCity(List<City> city) {
        this.city = city;
    }

    public List<State> getState() {
        return state;
    }

    public void setState(List<State> state) {
        this.state = state;
    }

    public List<District> getDistrict() {
        return district;
    }

    public void setDistrict(List<District> district) {
        this.district = district;
    }

}
