package com.example.foodgospel.Models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;


@Entity
public class SectorDBModel implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;


    public SectorDBModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
