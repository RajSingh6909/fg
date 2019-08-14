
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
public class MilkProduct implements Serializable {

    private int id;

    @NonNull
    @PrimaryKey
    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("produce_type")
    @Expose
    private String produceType;

    @SerializedName(value = "name" ,  alternate = {"product_name"})
    @Expose
    private String name;


    public MilkProduct() {

    }

    @Ignore
    public MilkProduct(int id, String productId, String name) {
        this.id = id;
        this.name = name;
        this.productId = productId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProduceType() {
        return produceType;
    }

    public void setProduceType(String produceType) {
        this.produceType = produceType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MilkProduct that = (MilkProduct) o;
        return Objects.equals(productId, that.getProductId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }


}
