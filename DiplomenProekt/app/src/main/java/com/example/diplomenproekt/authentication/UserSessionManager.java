package com.example.diplomenproekt.authentication;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;

import java.util.HashMap;

public class UserSessionManager {
    public static final String KEY_EMAIL = "email";
    public static final String KEY_NAME = "name";
    int PRIVATE_MODE = 0;
    Context _context;
    SharedPreferences.Editor editor;
    SharedPreferences pref;

    public UserSessionManager(Context paramContext)
    {
        this._context = paramContext;
        this.pref = this._context.getSharedPreferences("AndroidPref", this.PRIVATE_MODE);
        this.editor = this.pref.edit();
    }

    public void createUserLoginSession(String paramString1, String paramString2)
    {
        Date loggedInTime = Calendar.getInstance().getTime();
        Log.d("Logged Time", loggedInTime.toString());
        this.editor.putBoolean("IsUserLoggedIn", true);
        this.editor.putString("name", paramString1);
        this.editor.putString("email", paramString2);
//        editor.putLong("session_timeout", loggedInTime.);
        this.editor.commit();
    }

    public HashMap<String, String> getUserDetails()
    {
        HashMap<String, String> localHashMap = new HashMap<String, String>();
        localHashMap.put("name", this.pref.getString("name", null));
        localHashMap.put("email", this.pref.getString("email", null));
        return localHashMap;
    }

    public boolean isUserLoggedIn()
    {
        return this.pref.getBoolean("IsUserLoggedIn", false);
    }

    public void logoutUser()
    {
        this.editor.clear();
        this.editor.apply();
    }
}
