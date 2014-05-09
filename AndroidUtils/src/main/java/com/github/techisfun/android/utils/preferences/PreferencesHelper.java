package com.github.techisfun.android.utils.preferences;

import android.content.Context;
import android.preference.PreferenceManager;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

import com.google.gson.Gson;

public class PreferencesHelper {

    private Context mContext;

    private SharedPreferences mSharedPreferences;

    public PreferencesHelper(Context context) {
        mContext = context;

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    protected SharedPreferences getPreferences() {
        return mSharedPreferences;
    }

    public void putStringList(String key, ArrayList<String> items) {

        String joined = new Gson().toJson(items);

        mSharedPreferences.edit().putString(key, joined).commit();
    }

    public ArrayList<String> getStringList(String key, ArrayList<String> defaultItems) {
        String json = mSharedPreferences.getString(key, "");

        if ("".equals(json)) {
            return defaultItems;
        } else {
            return new Gson().fromJson(json, ArrayList.class);
        }
    }

    public ArrayList<String> getStringList(String key) {
        return getStringList(key, new ArrayList<String>(0));
    }

    public void putStringSet(String key, Set<String> items) {
        String json = new Gson().toJson(items);

        mSharedPreferences.edit().putString(key, json).commit();
    }

    public Set<String> getStringSet(String key, Set<String> defaultItems) {
        String json = mSharedPreferences.getString(key, "");

        if ("".equals(json)) {
            return defaultItems;
        } else {
            return new Gson().fromJson(json, Set.class);
        }
    }

    public Set<String> getStringSet(String key) {
        return getStringSet(key, new HashSet<String>(0));
    }
}