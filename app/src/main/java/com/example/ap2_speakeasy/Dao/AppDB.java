package com.example.ap2_speakeasy.Dao;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.ap2_speakeasy.entities.Contact;
import com.example.ap2_speakeasy.entities.Message;
import com.example.ap2_speakeasy.entities.User;
import com.example.ap2_speakeasy.messageConverter;
import com.example.ap2_speakeasy.userConvert;

@Database(entities = {Contact.class, Message.class}, version = 3)
@TypeConverters({userConvert.class, messageConverter.class})
 public abstract class AppDB extends RoomDatabase {
    public static final String DATABASE_NAME = "27017ChatDB.db";
     public abstract ContactDao contactDao();
     public abstract MessageDao messageDao();

}
