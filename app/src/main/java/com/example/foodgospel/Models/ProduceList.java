package com.example.foodgospel.Models;

public class ProduceList {

    int id ;
    String produceName;

    public ProduceList(int id, String produceName) {
        this.id = id;
        this.produceName = produceName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProduceName() {
        return produceName;
    }

    public void setProduceName(String produceName) {
        this.produceName = produceName;
    }
}
