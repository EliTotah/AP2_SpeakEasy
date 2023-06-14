package com.example.ap2_speakeasy;

import android.content.Context;

import androidx.room.Room;

import com.example.ap2_speakeasy.Dao.AppDB;

public class DatabaseManager {

    private static AppDB instance;

    public static synchronized AppDB getDatabase(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDB.class, "speakEasyDB")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
}
