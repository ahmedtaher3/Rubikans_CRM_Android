package com.devartlab.ui.main.ui.eShopping.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.devartlab.base.BaseApplication;
import com.devartlab.data.retrofit.RetrofitClient;
import com.devartlab.ui.main.ui.devartlink.letsTalk.model.user.UserResponse;
import com.devartlab.ui.main.ui.eShopping.main.model.login4EShopping.Login4EShoppingResponse;
import com.google.gson.Gson;

public class UserPreferenceHelper {
    private static SharedPreferences sharedPreferences = null;

    //here you can find shared preference operations like get saved data for user


    private UserPreferenceHelper() {

    }

    public static SharedPreferences getSharedPreferenceInstance(Context context) {
        if (sharedPreferences != null) return sharedPreferences;
        return sharedPreferences = context.getSharedPreferences("savedData",
                Context.MODE_PRIVATE);
    }

    public static void saveUserProfile(Login4EShoppingResponse user) {
        SharedPreferences.Editor prefsEditor = getSharedPreferenceInstance(BaseApplication
                .Companion.getInstance().getApplicationContext()).edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        prefsEditor.putString("userDetails", json);
        prefsEditor.apply();
    }

    public static Login4EShoppingResponse getUser() {
        Gson gson = new Gson();
        String json = getSharedPreferenceInstance(BaseApplication.Companion.getInstance().getApplicationContext())
                .getString("userDetails", "");
        if (json.equals("")) return new Login4EShoppingResponse();
        return gson.fromJson(json, Login4EShoppingResponse.class);
    }
    public static void saveUserProfileChat(UserResponse user) {
        SharedPreferences.Editor prefsEditor = getSharedPreferenceInstance(BaseApplication.Companion.getInstance().
                getApplicationContext()).edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        prefsEditor.putString("userDetails", json);
        prefsEditor.apply();
    }

    public static UserResponse getUserChat() {
        Gson gson = new Gson();
        String json = getSharedPreferenceInstance(BaseApplication.Companion.getInstance().getApplicationContext())
                .getString("userDetails", "");
        if (json.equals("")) return new UserResponse();
        return gson.fromJson(json, UserResponse.class);
    }
}
