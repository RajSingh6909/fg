package com.example.foodgospel.Models;

import java.io.Serializable;

public class PoultryGrown implements Serializable {

    int category_id = 0;
    String grown_name = "";

    public PoultryGrown() {
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getGrown_name() {
        return grown_name;
    }

    public void setGrown_name(String grown_name) {
        this.grown_name = grown_name;
    }
}
