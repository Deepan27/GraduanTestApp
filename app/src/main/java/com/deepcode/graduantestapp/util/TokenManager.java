package com.deepcode.graduantestapp.util;

import android.content.Context;
import android.content.SharedPreferences;

public class TokenManager {
    private static final String PREF_NAME = "com.deepcode.graduantestapp";
    private static final String KEY_ACCESS_TOKEN = "access_token";

    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;

    private static TokenManager instance;

    private TokenManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static synchronized TokenManager getInstance(Context context) {
        if (instance == null) {
            instance = new TokenManager(context);
        }
        return instance;
    }

    public void saveAccessToken(String accessToken) {
        editor.putString(KEY_ACCESS_TOKEN, accessToken);
        editor.apply();
    }

    public String getAccessToken() {
        return sharedPreferences.getString(KEY_ACCESS_TOKEN, null);
    }

    public void clearAccessToken() {
        editor.remove(KEY_ACCESS_TOKEN);
        editor.apply();
    }
}