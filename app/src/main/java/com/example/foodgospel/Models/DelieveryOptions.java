package com.example.foodgospel.Models;

public class DelieveryOptions {
    int id;
    String delieveryName;
    String delieveryNameCode;

    public DelieveryOptions(int id, String delieveryName, String delieveryNameCode) {
        this.id = id;
        this.delieveryName = delieveryName;
        this.delieveryNameCode = delieveryNameCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDelieveryName() {
        return delieveryName;
    }

    public void setDelieveryName(String delieveryName) {
        this.delieveryName = delieveryName;
    }

    public String getDelieveryNameCode() {
        return delieveryNameCode;
    }

    public void setDelieveryNameCode(String delieveryNameCode) {
        this.delieveryNameCode = delieveryNameCode;
    }
}
