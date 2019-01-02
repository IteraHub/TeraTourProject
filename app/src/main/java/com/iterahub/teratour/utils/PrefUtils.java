package com.iterahub.teratour.utils;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by ACER on 4/13/2018.
 */

public class PrefUtils {

    private final String USER_ID = "USER_ID";
    private final String LOGGED_IN = "LOGGED_IN";
    private final String AUTH = "AUTH";
    private final String FIRST_TIME = "FIRST_TIME";
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    public PrefUtils(Activity activity){
        prefs = PreferenceManager.getDefaultSharedPreferences(activity);
    }

    public void setUser(int userId){
        editor = prefs.edit();
        editor.putInt(USER_ID,userId);
        editor.apply();
    }

    public int getUser(){
        return prefs.getInt(USER_ID,0);
    }

    public void setUserId(String userId){
        editor = prefs.edit();
        editor.putString(USER_ID,userId);
        editor.apply();
    }

    public String getUserId(){
        return prefs.getString(USER_ID,"");
    }

    public void setLogIn(boolean value){
        editor = prefs.edit();
        editor.putBoolean(LOGGED_IN,value);
        editor.apply();
    }

    public boolean isFirstTime(){
        return prefs.getBoolean(FIRST_TIME,false);
    }

    public void setFirstTime(boolean value){
        editor = prefs.edit();
        editor.putBoolean(FIRST_TIME,value);
        editor.apply();
    }

    public boolean isLoggedIn(){
        return prefs.getBoolean(LOGGED_IN,false);
    }

    public void setAUTH(String auth){
        editor = prefs.edit();
        editor.putString(AUTH,auth);
        editor.apply();
    }

    public String getAuth(){
        return prefs.getString(AUTH,"");
    }

}
