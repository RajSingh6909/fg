
package com.example.foodgospel.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetRegisteredFarmerData {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("farmer")
    @Expose
    private Farmer farmer;

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

    public Farmer getFarmer() {
        return farmer;
    }

    public void setFarmer(Farmer farmer) {
        this.farmer = farmer;
    }

}
