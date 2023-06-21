package com.example.ap2_speakeasy;

import android.content.SharedPreferences;

public class ServerUrl {

    private static SharedPreferences preferences;
    //private String url = "http://10.0.2.2:5000/api/";

    private ServerUrl() {
        // Private constructor to prevent external instantiation
    }

    public static synchronized SharedPreferences getInstance(SharedPreferences pref) {
        if (preferences == null) {
            preferences = pref;
        }
        return preferences;
    }

    public static synchronized SharedPreferences getUrlInstance() {
        return preferences;
    }

}
