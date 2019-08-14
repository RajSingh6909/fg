package com.example.foodgospel.Models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.RoomWarnings;

import java.io.Serializable;

@Entity
public class State implements Serializable {
    @PrimaryKey
    private int id;

    private String name;

    private int state_statusId;

    public State() {

    }

    @Ignore
    public State(int id, String name, int state_statusId) {
        this.id = id;
        this.name = name;
        this.state_statusId = state_statusId;
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

    public int getState_statusId() {
        return state_statusId;
    }

    public void setState_statusId(int state_statusId) {
        this.state_statusId = state_statusId;
    }
}
