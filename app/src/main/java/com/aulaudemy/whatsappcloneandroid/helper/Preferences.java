package com.aulaudemy.whatsappcloneandroid.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.aulaudemy.whatsappcloneandroid.model.User;

import java.util.HashMap;

public class Preferences {

    private Context context;
    private SharedPreferences sharedPreferences;
    private static final String ARCHIVE_NAME = "whatsapp.preferences";
    private static int MODE = 0;
    private SharedPreferences.Editor editor;
    private static final String KEY_NAME  = "name";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_TOKEN = "token";

    public Preferences(Context contextParameter) {
        context = contextParameter;
        sharedPreferences = context.getSharedPreferences(ARCHIVE_NAME, MODE);
        editor = sharedPreferences.edit();//interface para fazer as edições nas preferencias
    }

    public void saveUsersPreferences( User user ) {
        editor.putString(KEY_NAME,  user.getName());
        editor.putString(KEY_TOKEN, user.getToken());
        editor.putString(KEY_PHONE, user.getPhone());
        editor.commit();
    }

    public HashMap<String, String> getUserData() {
        HashMap<String, String> userData = new HashMap<>();

        userData.put(KEY_NAME, sharedPreferences.getString(KEY_NAME, null));
        userData.put(KEY_PHONE, sharedPreferences.getString(KEY_PHONE, null));
        userData.put(KEY_TOKEN, sharedPreferences.getString(KEY_TOKEN, null));
        return userData;
    }
}
