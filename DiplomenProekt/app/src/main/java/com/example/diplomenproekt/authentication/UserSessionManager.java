package com.example.diplomenproekt.authentication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.diplomenproekt.AuthActivity;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

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
        Log.d("Logged Time", String.valueOf(loggedInTime.getTime()));
        this.editor.putBoolean("IsUserLoggedIn", true);
        this.editor.putString("name", paramString1);
        this.editor.putString("email", paramString2);
        this.editor.putLong("session_timeout", loggedInTime.getTime() + 60*1000);
        this.editor.putStringSet("device_addresses", new HashSet<String>());
        this.editor.commit();

//        new Timer().schedule(new TimerTask() {
//            @Override
//            public void run() {
//                logoutUser();
//            }
//        }, 60*1000);

    }

    public HashMap<String, String> getUserDetails()
    {
        HashMap<String, String> localHashMap = new HashMap<String, String>();
        localHashMap.put("name", this.pref.getString("name", null));
        localHashMap.put("email", this.pref.getString("email", null));
        return localHashMap;
    }

    public HashSet<String> getDeviceAddresses() {
        return (HashSet<String>) this.pref.getStringSet("device_addresses", new HashSet<String>());
    }

    public boolean updateDeviceAddresses(HashSet<String> newAddresses) {
        HashSet<String> newDeviceAddList = getDeviceAddresses();
        if(!newDeviceAddList.addAll(newAddresses)){
            return false;
        }
        this.editor.putStringSet("device_addresses", newDeviceAddList);
        this.editor.commit();
        return true;
    }

    public Long currentSessionTimeout() { return this.pref.getLong("session_timeout", 0); }

    public boolean isUserLoggedIn()
    {
        return this.pref.getBoolean("IsUserLoggedIn", false);
    }

    public void logoutUser()
    {
        Log.d("Logged user out", this.pref.toString());
        this.editor.clear();
        this.editor.apply();
        Intent intent = new Intent(_context, AuthActivity.class);
        _context.startActivity(intent);
    }
}
