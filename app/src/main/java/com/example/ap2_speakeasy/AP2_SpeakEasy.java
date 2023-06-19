package com.example.ap2_speakeasy;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class AP2_SpeakEasy extends Application {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public static Context context;
    @Override
    public void onCreate(){
        super.onCreate();
        context = getApplicationContext();
        preferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public SharedPreferences getPreferences() {
        return preferences;
    }

    public SharedPreferences.Editor getEditor() {
        return editor;
    }

    public void setPreferences(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    public void setEditor(SharedPreferences.Editor editor) {
        this.editor = editor;
    }
    public void set(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.apply();
    }

    public String get(String key) {
        return preferences.getString(key, "");
    }
}
