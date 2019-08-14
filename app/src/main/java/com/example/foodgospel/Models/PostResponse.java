package com.example.foodgospel.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostResponse {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("sync")
    @Expose
    private int sync;
    @SerializedName("farmer_id")
    @Expose
    private int farmer_id;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public int getSync() {
        return sync;
    }

    public void setSync(int sync) {
        this.sync = sync;
    }

    public int getFarmer_id() {
        return farmer_id;
    }

    public void setFarmer_id(int farmer_id) {
        this.farmer_id = farmer_id;
    }
}
