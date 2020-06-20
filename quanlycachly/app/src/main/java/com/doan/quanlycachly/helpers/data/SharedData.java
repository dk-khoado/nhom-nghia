package com.doan.quanlycachly.helpers.data;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedData {
    private SharedPreferences.Editor editor;

    private SharedPreferences preferences;
    private Context context;

    @SuppressLint("CommitPrefEdits")
    public SharedData(Context context) {
        this.context = context;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = preferences.edit();
    }

    public String getField(String name) {
        return preferences.getString(name, "");
    }

    public void setField(String name, String data) {
        editor.putString(name, data);
    }

    public void commit(){
        editor.commit();
    }
    public boolean isLogin() {
        String token = preferences.getString("token", "");
        if (token.equals("")) {
            return false;
        }
        return true;
    }

    public String getToken(){
        return preferences.getString("token","");
    }
    public void clear() {
        editor.clear();
        editor.commit();
    }
}
