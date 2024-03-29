
package com.example.foodgospel.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetAllRegionData {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("allData")
    @Expose
    private AllData allData;

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

    public AllData getAllData() {
        return allData;
    }

    public void setAllData(AllData allData) {
        this.allData = allData;
    }

}
