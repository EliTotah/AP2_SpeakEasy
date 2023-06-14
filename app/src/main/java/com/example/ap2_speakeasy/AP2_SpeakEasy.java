package com.example.ap2_speakeasy;

import android.app.Application;
import android.content.Context;

public class AP2_SpeakEasy extends Application {

    public static Context context;
    @Override
    public void onCreate(){
        super.onCreate();
        context = getApplicationContext();
    }
}
