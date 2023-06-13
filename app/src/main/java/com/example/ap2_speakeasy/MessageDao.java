package com.example.ap2_speakeasy;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MessageDao {
    @Query("SELECT * FROM message")
    List<Message> getMessages();

    @Query("SELECT * FROM message WHERE contactId = :id ORDER BY id")
    List<Message> getAllMessagesWithContact(int id);

    @Insert
    void insert(Message... messages);

    @Update
    void update(Message... messages);

    @Delete
    void delete(Message... messages);
}
