package com.adsmodule.api.AdsModule.PreferencesManager;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.tools.downaloader.adsmodule.AdsModule.Utils.Constants;
import com.tools.downaloader.adsmodule.R;


public class AppPreferences {
    private Context applicationContext;
    private Gson gson;
    private SharedPreferences sharedPreferences;

    public AppPreferences(Context applicationContext) {
        this.applicationContext = applicationContext;
        gson = new Gson();
        String preferencesName = applicationContext.getString(R.string.app_name);
        sharedPreferences = applicationContext.getSharedPreferences(preferencesName, Context.MODE_PRIVATE);
    }

    public boolean isFirstRun() {
        return getBoolean(Constants.IS_FIRST_RUN, true);
    }

    public void setFirstRun(boolean isFirstRun) {
        putBoolean(Constants.IS_FIRST_RUN, isFirstRun);
    }


    public void putString(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    public void putLong(String key, long value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public long getLong(String key, long defaultValue) {
        return sharedPreferences.getLong(key, defaultValue);
    }

    public void putInt(String key, int value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public int getInt(String key, int defaultValue) {
        return sharedPreferences.getInt(key, defaultValue);
    }

    public void putBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    public void putFloat(String key, float value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    public float getFloat(String key, float defaultValue) {
        return sharedPreferences.getFloat(key, defaultValue);
    }

    public void remove(String key) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }


    public void clearPreferences() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
