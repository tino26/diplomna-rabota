package com.example.diplomenproekt;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.diplomenproekt.authentication.UserSessionManager;

public class SplashActivity extends AppCompatActivity {
    boolean isUserLoggedIn = true;
    // User Session Manager Class
    UserSessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new UserSessionManager(getApplicationContext());
        isUserLoggedIn = session.isUserLoggedIn();
        Log.d("UserLoggedIn", String.valueOf(isUserLoggedIn));

        if(isUserLoggedIn)
        {
            Intent intent = new Intent(SplashActivity.this,MainActivity.class);
            startActivity(intent);
            SplashActivity.this.finish();
        }
        else
        {
            Intent intent = new Intent(SplashActivity.this,AuthActivity.class);
            startActivity(intent);
            SplashActivity.this.finish();
//            Thread timer = new Thread(){
//                public void run() {
//                    try
//                    {
//                        sleep(3000);
//                    }catch(InterruptedException e) { }
//                    finally
//                    {
//                        Intent intent = new Intent(SplashActivity.this,AuthActivity.class);
//                        startActivity(intent);
//                        SplashActivity.this.finish();
//                    }
//                }
//            };
//            timer.start();
        }
    }}
