package com.example.foodgospel.Global;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class FoodGospelSharedPrefernce {
    public static void setSharePrefernceData(Context ctx, String name, String flag) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(ctx).edit();
        editor.putString(name, flag);
        editor.commit();
    }

    public static String getSharePrefernceData(Context ctx, String name) {
        return PreferenceManager.getDefaultSharedPreferences(ctx).getString(name, "");
    }

    public static void setSharePrefernceBoolean(Context ctx, String name, boolean flag) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(ctx).edit();
        editor.putBoolean(name, flag);
        editor.commit();
    }

    public static boolean getSharePrefernceBoolean(Context ctx, String name) {
        return PreferenceManager.getDefaultSharedPreferences(ctx).getBoolean(name, false);
    }
}