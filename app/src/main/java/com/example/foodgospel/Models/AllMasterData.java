
package com.example.foodgospel.Models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllMasterData {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("sector")
    @Expose
    private List<Sector> sector = null;
    @SerializedName("category")
    @Expose
    private List<Category> category = null;
    @SerializedName("crop")
    @Expose
    private List<Crop> crop = null;
    @SerializedName("milk_product")
    @Expose
    private List<MilkProduct> milkProduct = null;
    @SerializedName("Milk_produce")
    @Expose
    private List<LiveStockGrown> liveStockGrowns = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<Sector> getSector() {
        return sector;
    }

    public void setSector(List<Sector> sector) {
        this.sector = sector;
    }

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }

    public List<Crop> getCrop() {
        return crop;
    }

    public void setCrop(List<Crop> crop) {
        this.crop = crop;
    }

    public List<MilkProduct> getMilkProduct() {
        return milkProduct;
    }

    public void setMilkProduct(List<MilkProduct> milkProduct) {
        this.milkProduct = milkProduct;
    }

    public List<LiveStockGrown> getLiveStockGrowns() {
        return liveStockGrowns;
    }

    public void setLiveStockGrowns(List<LiveStockGrown> liveStockGrowns) {
        this.liveStockGrowns = liveStockGrowns;
    }
}
