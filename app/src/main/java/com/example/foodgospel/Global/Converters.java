package com.example.foodgospel.Global;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by DESKTOP on 7/12/2017.
 */

public class Converters {
    @TypeConverter
    public static ArrayList<String> fromTimestamp(String value) {
        Type listType = new TypeToken<ArrayList<String>>() {}.getType();
        return new Gson().fromJson(value, listType);
       // return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static String arraylistToString(ArrayList<String> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);

        return json;
       // return date == null ? null : date.getTime();
    }
}
