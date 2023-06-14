package com.example.ap2_speakeasy.Dao;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.ap2_speakeasy.Dao.MessageDao;
import com.example.ap2_speakeasy.Dao.UserDao;
import com.example.ap2_speakeasy.entities.Message;
import com.example.ap2_speakeasy.entities.User;

@Database(entities = {User.class, Message.class}, version = 3)
 public abstract class AppDB extends RoomDatabase {
     public abstract UserDao userDao();
     public abstract MessageDao messageDao();

}
