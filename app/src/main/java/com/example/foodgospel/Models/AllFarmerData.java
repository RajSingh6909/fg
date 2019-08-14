
package com.example.foodgospel.Models;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllFarmerData implements Serializable {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("user_farmer")
    @Expose
    private List<UserFarmer> userFarmer = null;

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

    public List<UserFarmer> getUserFarmer() {
        return userFarmer;
    }

    public void setUserFarmer(List<UserFarmer> userFarmer) {
        this.userFarmer = userFarmer;
    }

}
