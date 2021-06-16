package com.omninos.nani.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.omninos.nani.modelClass.LoginRegisterModelClass;

public class AppPreferences {
    private static AppPreferences appPreference;
    private SharedPreferences sharedPreferences;

    private AppPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences("App_Nani", Context.MODE_PRIVATE);
    }

    public static AppPreferences init(Context context) {
        if (appPreference == null) {
            appPreference = new AppPreferences(context);
        }
        return appPreference;
    }


    public void SaveString(String key, String value) {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String GetString(String key) {

        return sharedPreferences.getString(key, "");
    }

    public void Logout() {
        sharedPreferences.edit().clear().apply();
    }

    public void saveUserDetails(LoginRegisterModelClass registerModelClass) {
        Gson gson = new Gson();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ConstantData.LOG_IN_DATA, gson.toJson(registerModelClass));
        editor.apply();
    }

    public LoginRegisterModelClass getUserDetails() {
        Gson gson = new Gson();
        return gson.fromJson(sharedPreferences.getString(ConstantData.LOG_IN_DATA, ""), LoginRegisterModelClass.class);
    }


}
