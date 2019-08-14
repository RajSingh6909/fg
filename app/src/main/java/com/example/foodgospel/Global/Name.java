package com.example.foodgospel.Global;

public class Name {

    private String Name;

    private int status;

    public Name(String name, int status) {
        Name = name;
        this.status = status;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
