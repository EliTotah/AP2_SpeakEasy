package com.example.ap2_speakeasy.Dao;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.ap2_speakeasy.entities.Contact;
import com.example.ap2_speakeasy.entities.Message;

@Database(entities = {Contact.class, Message.class}, version = 1)
 public abstract class AppDB extends RoomDatabase {
    public static final String DATABASE_NAME = "27017ChatDB.db";
     public abstract ContactDao contactDao();
     public abstract MessageDao messageDao();

}
