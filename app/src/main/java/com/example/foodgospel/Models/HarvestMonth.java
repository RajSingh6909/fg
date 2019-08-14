package com.example.foodgospel.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Objects;

public class HarvestMonth implements Serializable {
    int id;

    @SerializedName(value="monthName", alternate={"month"})
    String monthName;

    public HarvestMonth(int id, String monthName) {
        this.id = id;
        this.monthName = monthName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HarvestMonth that = (HarvestMonth) o;
        return Objects.equals(id, that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
